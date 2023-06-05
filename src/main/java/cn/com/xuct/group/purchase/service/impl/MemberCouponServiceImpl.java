/**
 * Copyright (C), 2015-2023, 楚恬商场
 * FileName: UserCouponServiceImpl
 * Author:   Derek Xu
 * Date:     2023/4/26 19:37
 * Description:
 * History:
 * <author>          <time>          <version>          <desc>
 * Derek Xu           修改时间           版本号              描述
 */
package cn.com.xuct.group.purchase.service.impl;

import cn.com.xuct.group.purchase.base.service.BaseServiceImpl;
import cn.com.xuct.group.purchase.base.vo.Column;
import cn.com.xuct.group.purchase.entity.Coupon;
import cn.com.xuct.group.purchase.entity.CouponWares;
import cn.com.xuct.group.purchase.entity.MemberCoupon;
import cn.com.xuct.group.purchase.mapper.CouponMapper;
import cn.com.xuct.group.purchase.mapper.CouponWaresMapper;
import cn.com.xuct.group.purchase.mapper.MemberCouponMapper;
import cn.com.xuct.group.purchase.service.CouponWaresService;
import cn.com.xuct.group.purchase.service.MemberCouponService;
import cn.hutool.core.date.DatePattern;
import cn.hutool.core.date.DateTime;
import cn.hutool.core.date.DateUtil;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.github.yulichang.wrapper.MPJLambdaWrapper;
import com.google.common.collect.Lists;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * 〈一句话功能简述〉<br>
 * 〈〉
 *
 * @author Derek Xu
 * @create 2023/4/26
 * @since 1.0.0
 */
@Service
@RequiredArgsConstructor
public class MemberCouponServiceImpl extends BaseServiceImpl<MemberCouponMapper, MemberCoupon> implements MemberCouponService {
    private final CouponMapper couponMapper;
    private final CouponWaresService couponWaresService;

    @Override
    public List<MemberCoupon> list(Long memberId, final Integer status) {
        Date now = DateUtil.parseDate(DateUtil.now());
        MPJLambdaWrapper<MemberCoupon> wrapper = this.buildQuery(memberId);
        switch (status) {
            case 1 , 2 ->
                    wrapper.le(MemberCoupon::getBeginTime, now).ge(MemberCoupon::getEndTime, now).eq(MemberCoupon::isUsed, status != 1);
            default -> wrapper.lt(MemberCoupon::getEndTime, now).eq(MemberCoupon::isUsed, false);
        }
        return super.getBaseMapper().selectList(wrapper.orderByAsc(Coupon::getPrice));
    }

    @Override
    public List<MemberCoupon> canUsed(Long memberId) {
        Date now = DateUtil.parseDate(DateUtil.now());
        MPJLambdaWrapper<MemberCoupon> wrapper = this.buildQuery(memberId).le(MemberCoupon::getBeginTime, now).ge(MemberCoupon::getEndTime, now);
        List<MemberCoupon> memberCoupons = super.getBaseMapper().selectList(wrapper);
        if (CollectionUtils.isEmpty(memberCoupons)) {
            return Lists.newArrayList();
        }
        List<MemberCoupon> allCoupons = memberCoupons.stream().filter(item -> item.getCouponWaresType() == 0).collect(Collectors.toList());
        List<MemberCoupon> waresCoupons = memberCoupons.stream().filter(item -> item.getCouponWaresType() == 1).toList();
        if (CollectionUtils.isEmpty(waresCoupons)) {
            return allCoupons;
        }
        QueryWrapper<CouponWares> cqr = couponWaresService.getQuery().in("coupon_id", waresCoupons.stream().map(MemberCoupon::getCouponId).collect(Collectors.toList()));
        List<CouponWares> couponWares = couponWaresService.list(cqr);
        if (CollectionUtils.isEmpty(couponWares)) {
            return allCoupons;
        }
        Map<Long, List<CouponWares>> collect = couponWares.stream().collect(Collectors.groupingBy(CouponWares::getCouponId));

        waresCoupons.forEach(item -> {
            if (collect.containsKey(item.getCouponId())) {
                item.setCouponWaresList(collect.get(item.getCouponId()));
            }
        });
        allCoupons.addAll(waresCoupons);
        return allCoupons;
    }

    @Override
    public void updateUserCouponUsed(Long couponId, boolean used) {
        MemberCoupon memberCoupon = new MemberCoupon();
        memberCoupon.setId(couponId);
        memberCoupon.setUsed(used);
        this.updateById(memberCoupon);
    }

    @Override
    public MemberCoupon getUserCoupon(final Long memberId, Long id) {
        return super.getBaseMapper().selectOne(this.buildQuery(memberId).eq(MemberCoupon::getId, id));
    }

    @Override
    public int addMemberCoupon(Long memberId, Long couponId, List<String> times) {
        Coupon coupon = couponMapper.selectById(couponId);
        if (coupon == null || coupon.isDeleted()) {
            return -1;
        }
        long count = this.count(Lists.newArrayList(Column.of("member_id", memberId), Column.of("coupon_id", couponId)));
        if (count > coupon.getMemberHasMax()) {
            return -2;
        }
        MemberCoupon memberCoupon = new MemberCoupon();
        memberCoupon.setMemberId(memberId);
        memberCoupon.setCouponId(couponId);
        memberCoupon.setBeginTime(DateUtil.parse(times.get(0)));
        memberCoupon.setEndTime(DateUtil.parse(times.get(1)));
        this.save(memberCoupon);
        return 0;
    }

    private MPJLambdaWrapper<MemberCoupon> buildQuery(final Long memberId) {
        return new MPJLambdaWrapper<MemberCoupon>()
                .selectAll(MemberCoupon.class)
                .selectAs(Coupon::getPrice, MemberCoupon::getCouponPrice)
                .selectAs(Coupon::getFullPrice, MemberCoupon::getCouponFullPrice)
                .selectAs(Coupon::getName, MemberCoupon::getCouponName)
                .selectAs(Coupon::getWaresType, MemberCoupon::getCouponWaresType)
                .innerJoin(Coupon.class, Coupon::getId, MemberCoupon::getCouponId)
                .eq(MemberCoupon::getMemberId, memberId)
                .eq(Coupon::isDeleted, false);
    }
}