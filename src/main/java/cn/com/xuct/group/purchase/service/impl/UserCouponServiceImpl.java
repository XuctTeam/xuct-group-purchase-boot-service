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
import com.github.yulichang.wrapper.MPJLambdaWrapper;
import org.springframework.stereotype.Service;

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
    public List<UserCoupon> list(Long userId) {
        MPJLambdaWrapper<UserCoupon> wrapper = new MPJLambdaWrapper<UserCoupon>()
                .selectAll(UserCoupon.class)//查询user表全部字段
                .selectAs(Coupon::getPrice, UserCoupon::getCouponPrice)
                .selectAs(Coupon::getFullPrice, UserCoupon::getCouponFullPrice)
                .selectAs(Coupon::getName, UserCoupon::getCouponName)
                .innerJoin(Coupon.class, Coupon::getId, UserCoupon::getCouponId)
                .eq(UserCoupon::getUserId, userId)
                .eq(Coupon::isUsed, false)
                .orderByDesc(UserCoupon::isUsed);
        return super.getBaseMapper().selectList(wrapper);
    }
}