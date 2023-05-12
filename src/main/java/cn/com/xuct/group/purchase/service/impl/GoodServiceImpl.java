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

import cn.com.xuct.group.purchase.base.service.BaseServiceImpl;
import cn.com.xuct.group.purchase.entity.Good;
import cn.com.xuct.group.purchase.mapper.GoodMapper;
import cn.com.xuct.group.purchase.service.GoodService;
import cn.com.xuct.group.purchase.vo.dto.GoodInventoryDto;
import cn.com.xuct.group.purchase.vo.result.GoodResult;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import lombok.extern.slf4j.Slf4j;
import org.assertj.core.util.Lists;
import org.springframework.stereotype.Service;

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
}