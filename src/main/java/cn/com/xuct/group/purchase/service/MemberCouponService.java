/**
 * Copyright (C), 2015-2023, 楚恬商场
 * FileName: UserCouponService
 * Author:   Derek Xu
 * Date:     2023/4/26 19:36
 * Description:
 * History:
 * <author>          <time>          <version>          <desc>
 * Derek Xu           修改时间           版本号              描述
 */
package cn.com.xuct.group.purchase.service;

import cn.com.xuct.group.purchase.base.service.IBaseService;
import cn.com.xuct.group.purchase.entity.MemberCoupon;
import cn.com.xuct.group.purchase.mapper.MemberCouponMapper;

import java.util.List;

/**
 * 〈一句话功能简述〉<br>
 * 〈〉
 *
 * @author Derek Xu
 * @create 2023/4/26
 * @since 1.0.0
 */
public interface MemberCouponService extends IBaseService<MemberCouponMapper, MemberCoupon> {

    /**
     * 优惠券列表
     *
     * @param memberId
     * @return
     */
    List<MemberCoupon> list(final Long memberId , final Integer status);

    /**
     * 可用的优惠券列表
     *
     * @param memberId
     * @return
     */
    List<MemberCoupon> canUsed(final Long memberId);


    /**
     * 更新优惠券
     *
     * @param id
     */
    void updateUserCouponUsed(final Long id);

    /**
     * 查询详情
     *
     * @param id
     * @return
     */
    MemberCoupon getUserCoupon(final Long memberId, final Long id);
}