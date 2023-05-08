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
import cn.com.xuct.group.purchase.entity.Coupon;
import cn.com.xuct.group.purchase.entity.UserCoupon;
import cn.com.xuct.group.purchase.mapper.UserCouponMapper;
import cn.com.xuct.group.purchase.service.UserCouponService;
import cn.hutool.core.date.DateUtil;
import com.github.yulichang.wrapper.MPJLambdaWrapper;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;

/**
 * 〈一句话功能简述〉<br>
 * 〈〉
 *
 * @author Derek Xu
 * @create 2023/4/26
 * @since 1.0.0
 */
@Service
public class UserCouponServiceImpl extends BaseServiceImpl<UserCouponMapper, UserCoupon> implements UserCouponService {

    @Override
    public List<UserCoupon> list(Long userId, final Integer status) {
        Date now = DateUtil.parseDate(DateUtil.now());
        MPJLambdaWrapper<UserCoupon> wrapper = this.buildQuery(userId);
        switch (status) {
            case 1 ->
                    wrapper.le(UserCoupon::getBeginTime, now).ge(UserCoupon::getEndTime, now).eq(UserCoupon::isUsed, false);
            case 2 -> wrapper.eq(UserCoupon::isUsed, false);
            default -> wrapper.lt(UserCoupon::getEndTime, now).eq(UserCoupon::isUsed, false);
        }
        return super.getBaseMapper().selectList(wrapper.orderByAsc(Coupon::getPrice));
    }

    @Override
    public List<UserCoupon> canUsed(Long userId) {
        Date now = DateUtil.parseDate(DateUtil.now());
        MPJLambdaWrapper<UserCoupon> wrapper = this.buildQuery(userId).le(UserCoupon::getBeginTime, now).ge(UserCoupon::getEndTime, now).orderByAsc(UserCoupon::isUsed).orderByAsc(Coupon::getPrice);
        return super.getBaseMapper().selectList(wrapper);
    }

    @Override
    public void updateUserCouponUsed(Long couponId) {
        UserCoupon userCoupon = new UserCoupon();
        userCoupon.setId(couponId);
        userCoupon.setUsed(true);
        this.updateById(userCoupon);
    }

    @Override
    public UserCoupon getUserCoupon(final Long userId, Long id) {
        return super.getBaseMapper().selectOne(this.buildQuery(userId).eq(UserCoupon::getId, id));
    }

    private MPJLambdaWrapper<UserCoupon> buildQuery(final Long userId) {
        return new MPJLambdaWrapper<UserCoupon>()
                .selectAll(UserCoupon.class)//查询user表全部字段
                .selectAs(Coupon::getPrice, UserCoupon::getCouponPrice)
                .selectAs(Coupon::getFullPrice, UserCoupon::getCouponFullPrice)
                .selectAs(Coupon::getName, UserCoupon::getCouponName)
                .innerJoin(Coupon.class, Coupon::getId, UserCoupon::getCouponId)
                .eq(UserCoupon::getUserId, userId)
                .eq(Coupon::isUsed, false);
    }
}