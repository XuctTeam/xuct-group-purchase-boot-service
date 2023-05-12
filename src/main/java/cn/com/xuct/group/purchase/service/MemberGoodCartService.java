/**
 * Copyright (C), 2015-2023, XXX有限公司
 * FileName: UserCartService
 * Author:   Derek Xu
 * Date:     2023/3/29 10:09
 * Description:
 * History:
 * <author>          <time>          <version>          <desc>
 * Derek Xu         修改时间           版本号              描述
 */
package cn.com.xuct.group.purchase.service;

import cn.com.xuct.group.purchase.base.service.IBaseService;
import cn.com.xuct.group.purchase.entity.MemberGoodCart;
import cn.com.xuct.group.purchase.mapper.MemberGoodCartMapper;
import cn.com.xuct.group.purchase.vo.result.CartResult;

import java.util.List;

/**
 * 〈一句话功能简述〉<br>
 * 〈〉
 *
 * @author Derek Xu
 * @create 2023/3/29
 * @since 1.0.0
 */
public interface MemberGoodCartService extends IBaseService<MemberGoodCartMapper, MemberGoodCart> {

    /**
     * 添加到购物车
     *
     * @param gid
     * @param memberId
     */
    void addCart(final Long gid, final Long memberId);

    /**
     * 查询购物车数据
     *
     * @param memberId
     * @return
     */
    List<CartResult> cartList(final Long memberId  , List<Long> gids);

    /**
     * 修改购物车数量
     *
     * @param memberId
     * @param gid
     * @param num
     */
    void updateCartGoodNum(final Long memberId, final Long gid, final Integer num);

    /**
     * 删除购物车中商品
     *
     * @param gids
     */
    void deleteCartGood(final List<Long> gids , final Long memberId);
}