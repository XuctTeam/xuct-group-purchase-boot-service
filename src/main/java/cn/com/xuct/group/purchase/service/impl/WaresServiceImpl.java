/**
 * Copyright (C), 2015-2023, XXX有限公司
 * FileName: WaresServiceImpl
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
import cn.com.xuct.group.purchase.constants.EventCodeEnum;
import cn.com.xuct.group.purchase.constants.RedisCacheConstants;
import cn.com.xuct.group.purchase.entity.Wares;
import cn.com.xuct.group.purchase.mapper.WaresMapper;
import cn.com.xuct.group.purchase.mapstruct.IAdminSelectedConvert;
import cn.com.xuct.group.purchase.mapstruct.IDelayedConvert;
import cn.com.xuct.group.purchase.service.WaresService;
import cn.com.xuct.group.purchase.utils.JsonUtils;
import cn.com.xuct.group.purchase.vo.dto.DelayMessage;
import cn.com.xuct.group.purchase.vo.dto.WaresInventoryDto;
import cn.com.xuct.group.purchase.vo.result.WaresResult;
import cn.com.xuct.group.purchase.vo.result.admin.AdminSelectedResult;
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
import java.util.stream.Collectors;

import static cn.com.xuct.group.purchase.config.DelayedQueueConfiguration.DELAYED_EXCHANGE_NAME;
import static cn.com.xuct.group.purchase.config.DelayedQueueConfiguration.DELAYED_ROUTING_KEY;

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
public class WaresServiceImpl extends BaseServiceImpl<WaresMapper, Wares> implements WaresService {


    private final StringRedisTemplate redisTemplate;

    private final RabbitTemplate rabbitTemplate;

    @Override
    public WaresResult getWareInfo(Long id, Long memberId) {
        return ((WaresMapper) super.getBaseMapper()).getWareInfo(id, memberId);
    }

    @Override
    public List<Wares> findList() {
        QueryWrapper<Wares> qr = this.getQuery();
        qr.select(Lists.newArrayList("id", "name", "first_drawing", "swiper_images", "tags", "start_time", "end_time", "inventory"));
        qr.eq("status", 1);
        qr.eq("deleted", false);
        qr.orderByDesc("create_time");
        return this.getBaseMapper().selectList(qr);
    }

    @Override
    public void updateWaresInventory(Map<Long, Integer> inventoryMap) {
        List<WaresInventoryDto> wares = Lists.newArrayList();
        WaresInventoryDto ware = null;
        for (Long wareId : inventoryMap.keySet()) {
            ware = new WaresInventoryDto();
            ware.setWaresId(wareId);
            ware.setNum(inventoryMap.get(wareId));
            wares.add(ware);
        }
        ((WaresMapper) super.getBaseMapper()).updateWaresInventory(wares);
    }

    @Override
    public PageData<Wares> pageWares(final String name, final Integer status, final Integer pageNum, final Integer pageSize) {
        List<Column> columnList = Lists.newArrayList(Column.of("deleted", false));
        if (StringUtils.hasLength(name)) {
            columnList.add(Column.of("name", name, ColumnEnum.like));
        }
        if (status != null) {
            columnList.add(Column.of("status", status));
        }
        return this.convert(this.pages(Page.of(pageNum, pageSize), columnList, Sort.of("create_time", SortEnum.desc)));
    }

    @Override
    public void changeWaresStatus(Long waresId, Integer status) {
        Wares wares = this.getById(waresId);
        if (wares == null) {
            log.error("WaresServiceImpl:: change wares status error , wares id = {}", waresId);
            return;
        }
        wares.setStatus(status);
        this.updateById(wares);
    }

    @Override
    public void deleteWares(Long waresId) {
        Wares wares = this.getById(waresId);
        if (wares == null) {
            log.error("WaresServiceImpl:: delete wares status error , wares id = {}", waresId);
            return;
        }
        wares.setDeleted(true);
        wares.setStatus(1);
        //删除redis中的库存
        redisTemplate.delete(RedisCacheConstants.WARES_INVENTORY_REDIS_KEY.concat(String.valueOf(waresId)));
        this.updateById(wares);
    }

    @Override
    public int addWares(Wares wares) {
        if (DateTime.now().isAfter(wares.getEndTime())) {
            return -1;
        }
        this.save(wares);
        /* 保存商品库存 */
        redisTemplate.opsForValue().increment(RedisCacheConstants.WARES_INVENTORY_REDIS_KEY.concat(String.valueOf(wares.getId())), wares.getInventory());
        /* 2. 增加商品过期时间 */
        String message = DelayMessage.ofMessage(EventCodeEnum.wares_expire, JsonUtils.obj2json(IDelayedConvert.INSTANCE.wares2Delayed(wares)));
        if (!StringUtils.hasLength(message)) {
            return -2;
        }
        log.info("WaresServiceImpl:: send wares delay message = {}", message);
        rabbitTemplate.convertAndSend(DELAYED_EXCHANGE_NAME, DELAYED_ROUTING_KEY, message,
                correlationData -> {
                    correlationData.getMessageProperties().setDelay(Long.valueOf(wares.getEndTime().getTime() - DateUtil.current()).intValue());
                    return correlationData;
                });
        return 0;
    }

    @Override
    public int editWares(Wares wares) {
        if (DateTime.now().isAfter(wares.getEndTime())) {
            return -1;
        }
        Wares existWares = this.getById(wares.getId());
        if (existWares == null) {
            return -2;
        }
        Integer inventory = existWares.getInventory();
        Date startTime = existWares.getStartTime();
        Date endTime = existWares.getEndTime();
        BeanUtils.copyProperties(wares, existWares);
        this.updateById(existWares);
        /* 1. 库存不等于 更新redis新的库存 */
        if (!String.valueOf(wares.getInventory()).equals(String.valueOf(inventory))) {
            redisTemplate.opsForValue().increment(RedisCacheConstants.WARES_INVENTORY_REDIS_KEY.concat(String.valueOf(wares.getId())), wares.getInventory());
        }
        /* 2. 增加商品过期时间 */
        if (DateUtil.isSameDay(wares.getStartTime(), startTime) && DateUtil.isSameDay(wares.getEndTime(), endTime)) {
            return 0;
        }
        String message = DelayMessage.ofMessage(EventCodeEnum.wares_expire, JsonUtils.obj2json(IDelayedConvert.INSTANCE.wares2Delayed(wares)));
        log.info("WaresServiceImpl:: send wares delay message = {}", message);
        if (!StringUtils.hasLength(message)) {
            return -2;
        }
        rabbitTemplate.convertAndSend(DELAYED_EXCHANGE_NAME, DELAYED_ROUTING_KEY, message,
                correlationData -> {
                    correlationData.getMessageProperties().setDelay(Long.valueOf(wares.getEndTime().getTime() - DateUtil.current()).intValue());
                    return correlationData;
                });
        return 0;
    }

    @Override
    public void removeWaresCategoryId(Long categoryId) {
        ((WaresMapper) super.getBaseMapper()).removeWaresCategoryId(categoryId);
    }

    @Override
    public void waresExpire(Long waresId, Integer version) {
        Wares wares = this.getById(waresId);
        if (wares == null) {
            log.error("WaresServiceImpl:: wares expire error , wares id = {}", wares);
            return;
        }
        if (wares.getStatus() == 0) {
            log.error("WaresServiceImpl:: wares not shelf , wares id = {}", wares);
            return;
        }
        if (!String.valueOf(wares.getVersion()).equals(String.valueOf(version + 1))) {
            log.error("WaresServiceImpl:: wares version change , wares id = {} , version = {}", wares, version);
            return;
        }
        wares.setStatus(0);
        this.updateById(wares);
    }

    @Override
    public List<AdminSelectedResult> getWaresSelected() {
        List<Wares> wares = this.find(Column.of("deleted", false));
        return wares.stream().map(IAdminSelectedConvert.INSTANCE::waresToSelected).collect(Collectors.toList());
    }
}