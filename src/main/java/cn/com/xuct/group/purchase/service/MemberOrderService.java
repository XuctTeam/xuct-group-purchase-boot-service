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
import cn.com.xuct.group.purchase.base.vo.PageData;
import cn.com.xuct.group.purchase.entity.MemberOrder;
import cn.com.xuct.group.purchase.entity.MemberOrderItem;
import cn.com.xuct.group.purchase.mapper.MemberOrderMapper;
import cn.com.xuct.group.purchase.vo.result.CartResult;
import cn.com.xuct.group.purchase.vo.result.OrderResult;
import cn.com.xuct.group.purchase.vo.result.OrderSumResult;
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
public interface MemberOrderService extends IBaseService<MemberOrderMapper, MemberOrder> {

    final String FROM_CART = "cart";

    final String FROM_WARES = "wares";

    /**
     * 订单总数统计
     *
     * @param memberId
     * @return
     */
    OrderSumResult sumCount(final Long memberId);

    /**
     * 获取确认订单详情
     *
     * @param memberId
     * @param scene
     * @param waresIds
     * @return
     */
    List<CartResult> getConfirmOrderDetail(final Long memberId, final String scene, List<Long> waresIds);

    /**
     * 保存订单
     *
     * @param memberId
     * @param scene     cart 购物车 wares 立即购买
     * @param addressId 配置地址
     * @param couponId  优惠券ID
     * @param integral
     * @param remarks
     * @param waresIds
     */
    String saveOrder(final Long memberId, final String scene, final Long addressId, Long couponId, Integer integral, final String remarks, List<Long> waresIds);

    /**
     * 订单分页查询
     *
     * @param memberId
     * @param status       状态
     * @param pageNo
     * @param pageSize
     * @param refundStatus
     * @param word         搜索关键词
     * @return
     */
    IPage<MemberOrder> findMemberOrder(final Long memberId, final Integer status, int pageNo, int pageSize, final Integer refundStatus);


    /**
     * 根据关键词搜索订单
     *
     * @param memberId
     * @param pageNo
     * @param pageSize
     * @param refund   是否是售后搜索  0 不是 1是
     * @param word
     * @return
     */
    IPage<MemberOrder> search(final Long memberId, int pageNo, int pageSize, final int refund, final String word);


    /**
     * 获取订单详情
     *
     * @param memberId
     * @param orderId
     * @return
     */
    OrderResult getDetail(final Long memberId, final Long orderId);

    /**
     * 申请取消订单
     *
     * @param memberId
     * @param orderId
     * @param reason
     * @return
     */
    String refundOrder(final Long memberId, final Long orderId, final String type, final String reason, final List<String> images);


    /**
     * 取消申请退单
     *
     * @param memberId
     * @param orderId
     */
    String cancelRefundOrder(final Long memberId, final Long orderId);

    /**
     * 取消订单
     *
     * @param memberId
     * @param orderId
     */
    String cancelOrder(final Long memberId, final Long orderId);

    /**
     * 催单
     *
     * @param memberId
     * @param orderId
     */
    void rushOrder(final Long memberId, final Long orderId);

    /**
     * 收货
     *
     * @param memberId
     * @param orderId
     */
    void receiveOrder(final Long memberId, final Long orderId);

    /**
     * 删除订单
     *
     * @param memberId
     * @param orderId
     */
    void deleteOrder(final Long memberId, final Long orderId);

    /**
     * 待评价商品
     *
     * @param memberId
     * @return
     */
    List<MemberOrderItem> evaluateList(final Long memberId);

    /**
     * 保存评价
     *
     * @param memberId
     * @param orderItemId
     * @param rate
     * @param evaluateImages
     * @param remarks
     */
    void evaluateWares(final Long memberId, final Long orderItemId, final String rate, final String evaluateImages, final String remarks);

    /**
     * 查询删除列表
     *
     * @param memberId
     * @return
     */
    List<MemberOrder> deleteList(final Long memberId);

    /**
     * 分页查询订单列表
     *
     * @param status
     * @param createTime
     * @param page
     * @param pageSize
     * @return
     */
    PageData<OrderResult> findAllMemberOrder(final Integer status, final List<String> createTime, final int page, final int pageSize);

}