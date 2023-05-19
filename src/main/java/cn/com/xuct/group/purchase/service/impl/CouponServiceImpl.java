/**
 * Copyright (C), 2015-2023, 楚恬商场
 * FileName: CouponServiceImpl
 * Author:   Derek Xu
 * Date:     2023/4/26 14:21
 * Description:
 * History:
 * <author>          <time>          <version>          <desc>
 * Derek Xu           修改时间           版本号              描述
 */
package cn.com.xuct.group.purchase.service.impl;

import cn.com.xuct.group.purchase.base.enums.ColumnEnum;
import cn.com.xuct.group.purchase.base.enums.SortEnum;
import cn.com.xuct.group.purchase.base.service.BaseServiceImpl;
import cn.com.xuct.group.purchase.base.vo.Column;
import cn.com.xuct.group.purchase.base.vo.PageData;
import cn.com.xuct.group.purchase.base.vo.Sort;
import cn.com.xuct.group.purchase.constants.EventCodeEnum;
import cn.com.xuct.group.purchase.entity.Coupon;
import cn.com.xuct.group.purchase.entity.CouponWares;
import cn.com.xuct.group.purchase.mapper.CouponMapper;
import cn.com.xuct.group.purchase.mapstruct.IDelayedConvert;
import cn.com.xuct.group.purchase.service.CouponService;
import cn.com.xuct.group.purchase.service.CouponWaresService;
import cn.com.xuct.group.purchase.utils.JsonUtils;
import cn.com.xuct.group.purchase.vo.dto.DelayMessage;
import cn.hutool.core.date.DateTime;
import cn.hutool.core.date.DateUtil;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.google.common.collect.Lists;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;
import org.springframework.util.StringUtils;

import java.util.HashMap;
import java.util.List;
import java.util.stream.Collectors;

import static cn.com.xuct.group.purchase.config.DelayedQueueConfiguration.DELAYED_EXCHANGE_NAME;
import static cn.com.xuct.group.purchase.config.DelayedQueueConfiguration.DELAYED_ROUTING_KEY;

/**
 * 〈一句话功能简述〉<br>
 * 〈〉
 *
 * @author Derek Xu
 * @create 2023/4/26
 * @since 1.0.0
 */
@Slf4j
@Service
@RequiredArgsConstructor
public class CouponServiceImpl extends BaseServiceImpl<CouponMapper, Coupon> implements CouponService {

    private final CouponWaresService couponWaresService;

    private final RabbitTemplate rabbitTemplate;

    @Override
    public PageData<Coupon> pages(String name, Integer pageNum, Integer pageSize) {
        List<Column> columnList = Lists.newArrayList();
        if (StringUtils.hasLength(name)) {
            columnList.add(Column.of("name", name, ColumnEnum.like));
        }
        return this.convert(this.pages(Page.of(pageNum, pageSize), columnList, Sort.of("id", SortEnum.asc)));
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public int addCoupon(Coupon coupon) {
        if (coupon.getWaresType() == 1 && CollectionUtils.isEmpty(coupon.getWaresIds())) {
            return -1;
        }
        if (coupon.getWaresType() == 0) {
            this.save(coupon);
            this.setCouponExpireDate(coupon);
            return 0;
        }
        this.save(coupon);
        this.saveCouponWares(coupon);
        this.setCouponExpireDate(coupon);
        return 0;
    }

    @Override
    public int editCoupon(Coupon coupon) {
        Coupon existCoupon = this.getById(coupon.getId());
        if (existCoupon == null) {
            return -1;
        }
        if (coupon.getWaresType() == 1 && CollectionUtils.isEmpty(coupon.getWaresIds())) {
            return -1;
        }
        BeanUtils.copyProperties(coupon, existCoupon);
        couponWaresService.removeByMap(new HashMap<>() {{
            put("coupon_id", coupon.getId());
        }});

        this.updateById(existCoupon);
        if (existCoupon.getWaresType() == 1) {
            this.saveCouponWares(existCoupon);
        }
        this.setCouponExpireDate(existCoupon);
        return 0;
    }

    @Override
    public void changeCouponStatus(Long couponId, Integer status) {
        Coupon existCoupon = this.getById(couponId);
        if (existCoupon == null) {
            return;
        }
        existCoupon.setUsed(status == 1);
        this.updateById(existCoupon);
    }

    @Override
    public void deleteCoupon(Long couponId) {
        Coupon existCoupon = this.getById(couponId);
        if (existCoupon == null) {
            return;
        }
        existCoupon.setDeleted(true);
        this.updateById(existCoupon);
    }

    @Override
    public void couponExpire(Long couponId, Integer version) {
        Coupon existCoupon = this.getById(couponId);
        if (existCoupon == null) {
            return;
        }
        if (existCoupon.getVersion() != (version + 1)) {
            log.error("CouponServiceImpl:: coupon expire version error , couponId = {} , version id = {}", couponId, version);
            return;
        }

        existCoupon.setUsed(false);
        this.updateById(existCoupon);
    }

    @Override
    public List<String> getCouponWaresId(Long couponId) {
        return couponWaresService.find(Column.of("coupon_id", couponId))
                .stream().map(CouponWares::getWaresId).map(String::valueOf).collect(Collectors.toList());
    }

    private void setCouponExpireDate(Coupon coupon) {
        // 永不过期
        if (coupon.getEffective() <= 0) {
            return;
        }
        DateTime expireTime = DateUtil.offsetDay(DateTime.now(), coupon.getEffective());
        String message = DelayMessage.ofMessage(EventCodeEnum.coupon_expire, JsonUtils.obj2json(IDelayedConvert.INSTANCE.coupon2Delayed(coupon)));
        if (!StringUtils.hasLength(message)) {
            return;
        }
        //发出优惠券过期延时
        rabbitTemplate.convertAndSend(DELAYED_EXCHANGE_NAME, DELAYED_ROUTING_KEY, message,
                correlationData -> {
                    correlationData.getMessageProperties().setDelay(Long.valueOf(expireTime.getTime()).intValue());
                    return correlationData;
                });
    }


    private void saveCouponWares(Coupon coupon) {
        List<CouponWares> couponWaresList = Lists.newArrayList();
        coupon.getWaresIds().forEach(waresId -> {
            CouponWares couponWares = new CouponWares();
            couponWares.setCouponId(coupon.getId());
            couponWares.setWaresId(waresId);
            couponWaresList.add(couponWares);
        });
        couponWaresService.saveBatch(couponWaresList);
    }
}