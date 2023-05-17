/**
 * Copyright (C), 2015-2023, XXX有限公司
 * FileName: UserCartServiceImpl
 * Author:   Derek Xu
 * Date:     2023/3/29 10:10
 * Description:
 * History:
 * <author>          <time>          <version>          <desc>
 * Derek Xu         修改时间           版本号              描述
 */
package cn.com.xuct.group.purchase.service.impl;

import cn.com.xuct.group.purchase.base.service.BaseServiceImpl;
import cn.com.xuct.group.purchase.base.vo.Column;
import cn.com.xuct.group.purchase.entity.MemberWaresCart;
import cn.com.xuct.group.purchase.mapper.MemberWaresCartMapper;
import cn.com.xuct.group.purchase.service.MemberWaresCartService;
import cn.com.xuct.group.purchase.vo.result.CartResult;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import lombok.extern.slf4j.Slf4j;
import org.assertj.core.util.Lists;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * 〈一句话功能简述〉<br>
 * 〈〉
 *
 * @author Derek Xu
 * @create 2023/3/29
 * @since 1.0.0
 */
@Slf4j
@Service
public class MemberWaresCartServiceImpl extends BaseServiceImpl<MemberWaresCartMapper, MemberWaresCart> implements MemberWaresCartService {

    @Override
    public void addCart(Long waresId, Long memberId) {
        ((MemberWaresCartMapper) super.getBaseMapper()).addWaresCart(waresId, memberId);
    }

    @Override
    public List<CartResult> cartList(Long memberId, List<Long> waresIds) {
        return ((MemberWaresCartMapper) super.getBaseMapper()).cartList(memberId, waresIds);
    }

    @Override
    public void updateCartWaresNum(Long memberId, Long waresIds, Integer num) {
        MemberWaresCart memberWaresCart = this.get(Lists.newArrayList(Column.of("member_id", memberId), Column.of("wares_id", waresIds)));
        if (memberWaresCart == null) {
            return;
        }
        memberWaresCart.setNum(num);
        this.updateById(memberWaresCart);
    }

    @Override
    public void deleteCartWares(List<Long> waresIds, final Long memberId) {
        QueryWrapper<MemberWaresCart> qr = super.getQuery();
        qr.eq("member_id", memberId).in("wares_id", waresIds);
        this.remove(qr);
    }
}