/**
 * Copyright (C), 2015-2023, XXX有限公司
 * FileName: GoodServiceImpl
 * Author:   Derek Xu
 * Date:     2023/3/27 11:24
 * Description:
 * History:
 * <author>          <time>          <version>          <desc>
 * Derek Xu         修改时间           版本号              描述
 */
package cn.com.xuct.group.purchase.service.impl;

import cn.com.xuct.group.purchase.base.enums.ColumnEnum;
import cn.com.xuct.group.purchase.base.enums.SortEnum;
import cn.com.xuct.group.purchase.base.service.BaseServiceImpl;
import cn.com.xuct.group.purchase.base.vo.Column;
import cn.com.xuct.group.purchase.base.vo.PageData;
import cn.com.xuct.group.purchase.base.vo.Sort;
import cn.com.xuct.group.purchase.constants.RedisCacheConstants;
import cn.com.xuct.group.purchase.entity.Good;
import cn.com.xuct.group.purchase.mapper.GoodMapper;
import cn.com.xuct.group.purchase.service.GoodService;
import cn.com.xuct.group.purchase.utils.JsonUtils;
import cn.com.xuct.group.purchase.vo.dto.GoodDelayedDto;
import cn.com.xuct.group.purchase.vo.dto.GoodInventoryDto;
import cn.com.xuct.group.purchase.vo.result.GoodResult;
import cn.hutool.core.date.DateTime;
import cn.hutool.core.date.DateUtil;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.assertj.core.util.Lists;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.BeanUtils;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.util.Date;
import java.util.List;
import java.util.Map;

import static cn.com.xuct.group.purchase.config.DelayedQueueConfiguration.*;

/**
 * 〈一句话功能简述〉<br>
 * 〈〉
 *
 * @author Derek Xu
 * @create 2023/3/27
 * @since 1.0.0
 */
@Slf4j
@Service
@RequiredArgsConstructor
public class GoodServiceImpl extends BaseServiceImpl<GoodMapper, Good> implements GoodService {


    private final StringRedisTemplate redisTemplate;

    private final RabbitTemplate rabbitTemplate;

    @Override
    public GoodResult getGood(Long id, Long memberId) {
        return ((GoodMapper) super.getBaseMapper()).getGoodInfo(id, memberId);
    }

    @Override
    public List<Good> findList() {
        QueryWrapper<Good> qr = this.getQuery();
        qr.select(Lists.newArrayList("id", "name", "first_drawing", "swiper_images", "tags", "start_time", "end_time", "inventory"));
        qr.eq("status", 1);
        qr.eq("deleted", false);
        qr.orderByDesc("create_time");
        return this.getBaseMapper().selectList(qr);
    }

    @Override
    public void updateGoodInventory(Map<Long, Integer> inventoryMap) {
        List<GoodInventoryDto> goods = Lists.newArrayList();
        GoodInventoryDto good = null;
        for (Long gid : inventoryMap.keySet()) {
            good = new GoodInventoryDto();
            good.setGoodId(gid);
            good.setNum(inventoryMap.get(gid));
            goods.add(good);
        }
        ((GoodMapper) super.getBaseMapper()).updateGoodInventory(goods);
    }

    @Override
    public PageData<Good> pageGoods(String name, Integer pageNum, Integer pageSize) {
        List<Column> columnList = Lists.newArrayList(Column.of("deleted", false));
        if (StringUtils.hasLength(name)) {
            columnList.add(Column.of("name", name, ColumnEnum.like));
        }
        return this.convert(this.pages(Page.of(pageNum, pageSize), columnList, Sort.of("create_time", SortEnum.desc)));
    }

    @Override
    public void changeGoodStatus(Long goodId, Integer status) {
        Good good = this.getById(goodId);
        if (good == null) {
            log.error("GoodServiceImpl:: change good status error , good id = {}", goodId);
            return;
        }
        good.setStatus(status);
        this.updateById(good);
    }

    @Override
    public void deleteGood(Long goodId) {
        Good good = this.getById(goodId);
        if (good == null) {
            log.error("GoodServiceImpl:: delete good status error , good id = {}", goodId);
            return;
        }
        good.setDeleted(true);
        good.setStatus(1);
        //删除redis中的库存
        redisTemplate.delete(RedisCacheConstants.GOOD_INVENTORY_REDIS_KEY.concat(String.valueOf(goodId)));
        this.updateById(good);
    }

    @Override
    public int addGood(Good good) {
        if (DateTime.now().isAfter(good.getEndTime())) {
            return -1;
        }
        this.save(good);
        /* 保存商品库存 */
        redisTemplate.opsForValue().increment(RedisCacheConstants.GOOD_INVENTORY_REDIS_KEY.concat(String.valueOf(good.getId())), good.getInventory());
        /* 2. 增加商品过期时间 */
        String message = JsonUtils.obj2json(GoodDelayedDto.builder().goodId(good.getId()).version(good.getVersion()).build());
        log.info("GoodServiceImpl:: send good delay message = {}", message);
        rabbitTemplate.convertAndSend(DELAYED_EXCHANGE_NAME, DELAYED_ROUTING_KEY, message,
                correlationData -> {
                    correlationData.getMessageProperties().setDelay(Long.valueOf(good.getEndTime().getTime() - DateUtil.current()).intValue());
                    return correlationData;
                });
        return 0;
    }

    @Override
    public int editGood(Good good) {
        if (DateTime.now().isAfter(good.getEndTime())) {
            return -1;
        }
        Good existGood = this.getById(good.getId());
        if (existGood == null) {
            return -2;
        }
        Integer inventory = existGood.getInventory();
        Date startTime = existGood.getStartTime();
        Date endTime = existGood.getEndTime();
        BeanUtils.copyProperties(good, existGood);
        this.updateById(existGood);
        /* 1. 库存不等于 更新redis新的库存 */
        if (!String.valueOf(good.getInventory()).equals(String.valueOf(inventory))) {
            redisTemplate.opsForValue().increment(RedisCacheConstants.GOOD_INVENTORY_REDIS_KEY.concat(String.valueOf(good.getId())), good.getInventory());
        }
        /* 2. 增加商品过期时间 */
        if (DateUtil.isSameDay(good.getStartTime(), startTime) && DateUtil.isSameDay(good.getEndTime(), endTime)) {
            return 0;
        }
        String message = JsonUtils.obj2json(GoodDelayedDto.builder().goodId(good.getId()).version(good.getVersion()).build());
        log.info("GoodServiceImpl:: send good delay message = {}", message);
        rabbitTemplate.convertAndSend(DELAYED_EXCHANGE_NAME, DELAYED_ROUTING_KEY, message,
                correlationData -> {
                    correlationData.getMessageProperties().setDelay(Long.valueOf(good.getEndTime().getTime() - DateUtil.current()).intValue());
                    return correlationData;
                });
        return 0;
    }
}