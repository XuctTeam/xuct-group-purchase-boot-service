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
import cn.com.xuct.group.purchase.mapper.CouponMapper;
import cn.com.xuct.group.purchase.service.CouponService;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.google.common.collect.Lists;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

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
public class CouponServiceImpl extends BaseServiceImpl<CouponMapper, Coupon> implements CouponService {

    @Override
    public PageData<Coupon> pages(String name, Integer pageNum, Integer pageSize) {
        List<Column> columnList = Lists.newArrayList();
        if (StringUtils.hasLength(name)) {
            columnList.add(Column.of("name", name, ColumnEnum.like));
        }
        return this.convert(this.pages(Page.of(pageNum, pageSize), columnList, Sort.of("id", SortEnum.asc)));
    }
}