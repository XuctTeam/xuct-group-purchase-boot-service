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
import cn.com.xuct.group.purchase.entity.MemberGoodCart;
import cn.com.xuct.group.purchase.mapper.MemberGoodCartMapper;
import cn.com.xuct.group.purchase.service.MemberGoodCartService;
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
public class MemberGoodCartServiceImpl extends BaseServiceImpl<MemberGoodCartMapper, MemberGoodCart> implements MemberGoodCartService {

    @Override
    public void addCart(Long gid, Long memberId) {
        ((MemberGoodCartMapper) super.getBaseMapper()).addGoodCart(gid, memberId);
    }

    @Override
    public List<CartResult> cartList(Long memberId, List<Long> gids) {
        return ((MemberGoodCartMapper) super.getBaseMapper()).cartList(memberId, gids);
    }

    @Override
    public void updateCartGoodNum(Long memberId, Long gid, Integer num) {
        MemberGoodCart memberGoodCart = this.get(Lists.newArrayList(Column.of("member_id", memberId), Column.of("good_id", gid)));
        if (memberGoodCart == null) {
            return;
        }
        memberGoodCart.setNum(num);
        this.updateById(memberGoodCart);
    }

    @Override
    public void deleteCartGood(List<Long> gids, final Long memberId) {
        QueryWrapper<MemberGoodCart> qr = super.getQuery();
        qr.eq("member_id", memberId).in("good_id", gids);
        this.remove(qr);
    }
}