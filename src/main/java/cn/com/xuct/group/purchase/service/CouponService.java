/**
 * Copyright (C), 2015-2023, 楚恬商场
 * FileName: CouponService
 * Author:   Derek Xu
 * Date:     2023/4/26 14:21
 * Description:
 * History:
 * <author>          <time>          <version>          <desc>
 * Derek Xu           修改时间           版本号              描述
 */
package cn.com.xuct.group.purchase.service;

import cn.com.xuct.group.purchase.base.service.IBaseService;
import cn.com.xuct.group.purchase.base.vo.PageData;
import cn.com.xuct.group.purchase.entity.Coupon;
import cn.com.xuct.group.purchase.mapper.CouponMapper;

/**
 * 〈一句话功能简述〉<br>
 * 〈〉
 *
 * @author Derek Xu
 * @create 2023/4/26
 * @since 1.0.0
 */
public interface CouponService extends IBaseService<CouponMapper, Coupon> {

    /**
     * 分页查询优惠券
     *
     * @param name
     * @param pageNum
     * @param pageSize
     * @return
     */
    PageData<Coupon> pages(final String name, final Integer pageNum, final Integer pageSize);
}