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
import cn.com.xuct.group.purchase.entity.Coupon;
import cn.com.xuct.group.purchase.entity.CouponWares;
import cn.com.xuct.group.purchase.mapper.CouponMapper;
import cn.com.xuct.group.purchase.service.CouponService;
import cn.com.xuct.group.purchase.service.CouponWaresService;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.google.common.collect.Lists;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;
import org.springframework.util.StringUtils;

import java.util.HashMap;
import java.util.List;

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
            return 0;
        }
        this.save(coupon);
        this.saveCouponWares(coupon);
        return 0;
    }

    @Override
    public int editCoupon(Coupon coupon) {
        Coupon existCoupon = this.getById(coupon.getId());
        boolean deleteFlag = existCoupon.getWaresType() != coupon.getWaresType();
        if (!deleteFlag && coupon.getWaresType() == 0) {
            this.updateById(coupon);
            return 0;
        }
        if (coupon.getWaresType() == 1 && CollectionUtils.isEmpty(coupon.getWaresIds())) {
            return -1;
        }
        couponWaresService.removeByMap(new HashMap<>() {{
            put("coupon_id", coupon.getId());
        }});
        this.updateById(coupon);
        this.saveCouponWares(coupon);
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