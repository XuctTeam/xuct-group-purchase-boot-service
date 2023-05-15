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
import cn.com.xuct.group.purchase.entity.Good;
import cn.com.xuct.group.purchase.mapper.GoodMapper;
import cn.com.xuct.group.purchase.service.GoodService;
import cn.com.xuct.group.purchase.vo.dto.GoodInventoryDto;
import cn.com.xuct.group.purchase.vo.result.GoodResult;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import lombok.extern.slf4j.Slf4j;
import org.assertj.core.util.Lists;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.util.List;
import java.util.Map;

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
public class GoodServiceImpl extends BaseServiceImpl<GoodMapper, Good> implements GoodService {

    @Override
    public GoodResult getGood(Long id, Long memberId) {
        return ((GoodMapper) super.getBaseMapper()).getGoodInfo(id, memberId);
    }

    @Override
    public List<Good> findList() {
        QueryWrapper<Good> qr = this.getQuery();
        qr.select(Lists.newArrayList("id", "name", "first_drawing", "swiper_images", "start_time", "end_time", "inventory"));
        qr.eq("status", 1);
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
        List<Column> columnList = Lists.newArrayList();
        if (StringUtils.hasLength(name)) {
            columnList.add(Column.of("name", name, ColumnEnum.like));
        }
        return this.convert(this.pages(Page.of(pageNum, pageSize), columnList, Sort.of("create_time", SortEnum.desc)));
    }
}