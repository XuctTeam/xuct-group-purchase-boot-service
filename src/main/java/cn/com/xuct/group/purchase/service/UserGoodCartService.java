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
import cn.com.xuct.group.purchase.entity.UserGoodCart;
import cn.com.xuct.group.purchase.mapper.UserGoodCartMapper;
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
public interface UserGoodCartService extends IBaseService<UserGoodCartMapper, UserGoodCart> {

    /**
     * 添加到购物车
     *
     * @param gid
     * @param uid
     */
    void addCart(final Long gid, final Long uid);

    /**
     * 查询购物车数据
     *
     * @param uid
     * @return
     */
    List<CartResult> cartList(final Long uid  , List<Long> gids);

    /**
     * 修改购物车数量
     *
     * @param uid
     * @param gid
     * @param num
     */
    void updateCartGoodNum(final Long uid, final Long gid, final Integer num);

    /**
     * 删除购物车中商品
     *
     * @param gids
     */
    void deleteCartGood(final List<Long> gids , final Long uid);
}