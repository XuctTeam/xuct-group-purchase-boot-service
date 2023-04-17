/**
 * Copyright (C), 2015-2023, 263企业通信
 * FileName: UserOrderService
 * Author:   Derek Xu
 * Date:     2023/4/7 11:19
 * Description:
 * History:
 * <author>          <time>          <version>          <desc>
 * 作者姓名           修改时间           版本号              描述
 */
package cn.com.xuct.group.purchase.service;

import cn.com.xuct.group.purchase.base.service.IBaseService;
import cn.com.xuct.group.purchase.entity.UserOrder;
import cn.com.xuct.group.purchase.mapper.UserOrderMapper;
import cn.com.xuct.group.purchase.vo.result.CartResult;
import cn.com.xuct.group.purchase.vo.result.OrderResult;
import com.baomidou.mybatisplus.core.metadata.IPage;

import java.util.List;

/**
 * 〈一句话功能简述〉<br>
 * 〈〉
 *
 * @author Derek Xu
 * @create 2023/4/7
 * @since 1.0.0
 */
public interface UserOrderService extends IBaseService<UserOrderMapper, UserOrder> {

    final String FROM_CART = "cart";

    final String FROM_GOOD = "good";

    /**
     * 获取确认订单详情
     *
     * @param userId
     * @param scene
     * @param gids
     * @return
     */
    List<CartResult> getConfirmOrderDetail(final Long userId, final String scene, List<Long> gids);

    /**
     * 保存订单
     *
     * @param userId
     * @param scene     cart 购物车 good立即购买
     * @param addressId 配置地址
     * @param integral
     * @param remarks
     * @param goodIds
     */
    String saveOrder(final Long userId, final String scene, final Long addressId, final Integer integral, final String remarks, List<Long> goodIds);

    /**
     * 订单分页查询
     *
     * @param userId
     * @param status   状态
     * @param pageNo
     * @param pageSize
     * @return
     */
    IPage<UserOrder> list(final Long userId, final Integer status, int pageNo, int pageSize);

    /**
     * 获取订单详情
     *
     * @param userId
     * @param orderId
     * @return
     */
    OrderResult getDetail(final Long userId, final Long orderId);

    /**
     * 申请取消订单
     *
     * @param userId
     * @param orderId
     * @param reason
     * @return
     */
    String refundOrder(final Long userId, final Long orderId, final String reason);

    /**
     * 取消订单
     *
     * @param userId
     * @param orderId
     */
    String cancelOrder(final Long userId, final Long orderId);

    /**
     * 催单
     *
     * @param userId
     * @param orderId
     */
    void rushOrder(final Long userId, final Long orderId);

    /**
     * 收货
     *
     * @param userId
     * @param orderId
     */
    void receiveOrder(final Long userId, final Long orderId);

}