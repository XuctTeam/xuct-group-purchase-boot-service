/**
 * Copyright (C), 2015-2023, 263企业通信
 * FileName: MemberOrderServiceImpl
 * Author:   Derek Xu
 * Date:     2023/4/7 14:17
 * Description:
 * History:
 * <author>          <time>          <version>          <desc>
 * 作者姓名           修改时间           版本号              描述
 */
package cn.com.xuct.group.purchase.service.impl;

import cn.com.xuct.group.purchase.base.service.BaseServiceImpl;
import cn.com.xuct.group.purchase.base.vo.Column;
import cn.com.xuct.group.purchase.base.vo.PageData;
import cn.com.xuct.group.purchase.constants.EventCodeEnum;
import cn.com.xuct.group.purchase.constants.RedisCacheConstants;
import cn.com.xuct.group.purchase.entity.*;
import cn.com.xuct.group.purchase.event.OrderEvent;
import cn.com.xuct.group.purchase.mapper.MemberOrderMapper;
import cn.com.xuct.group.purchase.service.*;
import cn.com.xuct.group.purchase.utils.JsonUtils;
import cn.com.xuct.group.purchase.utils.SpringContextUtils;
import cn.com.xuct.group.purchase.vo.dto.DelayMessage;
import cn.com.xuct.group.purchase.vo.dto.OrderReceiveDelayedDto;
import cn.com.xuct.group.purchase.vo.result.CartResult;
import cn.com.xuct.group.purchase.vo.result.OrderResult;
import cn.com.xuct.group.purchase.vo.result.OrderSumResult;
import cn.hutool.core.date.DateTime;
import cn.hutool.core.date.DateUtil;
import cn.hutool.core.lang.Validator;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.google.common.collect.Maps;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.assertj.core.util.Lists;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.BeanUtils;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;

import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import static cn.com.xuct.group.purchase.config.DelayedQueueConfiguration.DELAYED_EXCHANGE_NAME;
import static cn.com.xuct.group.purchase.config.DelayedQueueConfiguration.DELAYED_ROUTING_KEY;
import static cn.com.xuct.group.purchase.constants.RConstants.*;

/**
 * 〈一句话功能简述〉<br>
 * 〈〉
 *
 * @author Derek Xu
 * @create 2023/4/7
 * @since 1.0.0
 */
@Service
@RequiredArgsConstructor
@Slf4j
public class MemberOrderServiceImpl extends BaseServiceImpl<MemberOrderMapper, MemberOrder> implements MemberOrderService {
    private final MemberWaresCartService memberWaresCartService;
    private final MemberOrderItemService memberOrderItemService;
    private final WaresService waresService;
    private final MemberService memberService;
    private final MemberCouponService memberCouponService;
    private final StringRedisTemplate redisTemplate;
    private final RabbitTemplate rabbitTemplate;

    @Override
    public OrderSumResult sumCount(Long memberId) {
        OrderSumResult result = ((MemberOrderMapper) this.getBaseMapper()).sumCount(memberId);
        result.setToBeEvaluationCount(memberOrderItemService.countEvaluation(memberId));
        return result;
    }

    @Override
    public List<CartResult> getConfirmOrderDetail(Long memberId, String scene, List<Long> waresIds) {
        if (FROM_CART.equals(scene)) {
            return memberWaresCartService.cartList(memberId, waresIds);
        }
        CartResult cartResult = new CartResult();
        Wares wares = waresService.getById(waresIds.get(0));
        if (wares == null) {
            return Lists.newArrayList();
        }
        BeanUtils.copyProperties(wares, cartResult);
        cartResult.setWaresId(wares.getId());
        cartResult.setNum(1);
        return Lists.newArrayList(cartResult);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public String saveOrder(final Long memberId, final String scene, final Long addressId, Long couponId, final String remarks, List<Long> waresIds) {
        log.info("MemberOrderServiceImpl:: save order , member id = {} , address id = {} , couponId id = {} , waresIds = {}", memberId, addressId, couponId, JsonUtils.obj2json(waresIds));
        Member member = memberService.getById(memberId);
        if (member == null) {
            return MEMBER_NOT_EXIST;
        }
        couponId = this.checkCoupon(couponId, memberId);
        if (couponId != null && (couponId == -1L || couponId == -2L)) {
            return COUPON_NOT_EXIST;
        }
        switch (scene) {
            case FROM_CART -> {
                return this.saveCartWaresOrder(memberId, member.getOpenId(), addressId, couponId, remarks, waresIds);
            }
            case FROM_WARES -> {
                return this.saveBuyingOutrightOrder(memberId, member.getOpenId(), addressId, couponId, remarks, waresIds.get(0));
            }
            default -> {
                return ORDER_SCENE_ERROR;
            }
        }
    }

    @Override
    public IPage<MemberOrder> search(final Long memberId, int pageNo, int pageSize, final int refund, final String word) {
        String wordId = word;
        String wordWaresName = null;
        if (Validator.hasChinese(word) || Validator.isWord(word)) {
            wordWaresName = word;
            wordId = null;
        }
        return ((MemberOrderMapper) this.getBaseMapper()).search(memberId, Page.of(pageNo, pageSize), refund, wordId, wordWaresName);
    }

    @Override
    public IPage<MemberOrder> findMemberOrder(Long memberId, Integer status, int pageNo, int pageSize, final Integer refundStatus) {
        return ((MemberOrderMapper) this.getBaseMapper()).findMemberOrder(memberId, status, Page.of(pageNo, pageSize), refundStatus);
    }

    @Override
    public OrderResult getDetail(Long orderId, Long memberId) {
        OrderResult order = ((MemberOrderMapper) this.getBaseMapper()).getOrderDetail(orderId);
        if (order == null) {
            return null;
        }
        if (order.getUserCouponId() == null) {
            return order;
        }
        order.setCoupon(memberCouponService.getUserCoupon(memberId != null ? memberId : order.getMemberId(), order.getUserCouponId()));
        return order;
    }

    @Override
    public String refundOrder(final Long memberId, final Long orderId, final String type, final String reason, final List<String> images) {
        MemberOrder memberOrder = this.getById(orderId);
        if (memberOrder == null) {
            return ORDER_NOT_EXIST;
        }
        if (memberOrder.getRefundStatus() != 0) {
            return ORDER_ALREADY_REFUND;
        }
        memberOrder.setRefundStatus(1);
        memberOrder.setRefundType(type);
        memberOrder.setRefundReason(reason);
        memberOrder.setRefundTime(new Date());
        if (!CollectionUtils.isEmpty(images)) {
            memberOrder.setRefundImages(String.join(",", images.toArray(new String[0])));
        }
        this.updateById(memberOrder);
        return String.valueOf(SUCCESS);
    }

    @Override
    public String cancelRefundOrder(Long memberId, Long orderId) {
        MemberOrder memberOrder = this.getById(orderId);
        if (memberOrder == null) {
            return ORDER_NOT_EXIST;
        }
        if (memberOrder.getRefundStatus() != 1) {
            return ORDER_NOT_REFUND;
        }
        memberOrder.setRefundStatus(3);
        this.updateById(memberOrder);
        return String.valueOf(SUCCESS);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public String cancelOrder(Long memberId, Long orderId) {
        MemberOrder memberOrder = this.getById(orderId);
        if (memberOrder == null) {
            return ORDER_NOT_EXIST;
        }
        List<MemberOrderItem> memberOrderItems = memberOrderItemService.find(Column.of("order_id", orderId));
        if (CollectionUtils.isEmpty(memberOrderItems)) {
            return ERROR;
        }
        List<Wares> waresList = waresService.find(Column.in("id", memberOrderItems.stream().map(MemberOrderItem::getWaresId).collect(Collectors.toList())));
        Date date = DateUtil.date().toJdkDate();
        boolean waresExpire = waresList.stream().anyMatch(x -> x.getEndTime().getTime() <= date.getTime());
        if (waresExpire) {
            return ORDER_WARES_EXPIRE;
        }
        Map<Long, Integer> inventoryMap = memberOrderItems.stream().collect(Collectors.toMap(MemberOrderItem::getMemberId, MemberOrderItem::getNum));
        /* 1.更新商品库存 */
        for (Long gid : inventoryMap.keySet()) {
            redisTemplate.opsForValue().increment(RedisCacheConstants.WARES_INVENTORY_REDIS_KEY.concat(String.valueOf(gid)), inventoryMap.get(gid));
        }
        waresList.forEach(g -> {
            g.setInventory(g.getInventory() + inventoryMap.get(g.getId()));
        });
        waresService.updateBatchById(waresList);
        /* 2.更新订单状态 */
        memberOrder.setDeleted(true);
        this.updateById(memberOrder);
        return String.valueOf(SUCCESS);
    }

    @Override
    public void rushOrder(Long memberId, Long orderId) {
        MemberOrder memberOrder = this.getById(orderId);
        if (memberOrder == null || String.valueOf(orderId).equals(String.valueOf(memberOrder.getMemberId()))) {
            log.error("MemberOrderServiceImpl:: rush order error , order id = {}", orderId);
            return;
        }
        memberOrder.setRush(true);
        this.updateById(memberOrder);
    }

    @Override
    public void receiveOrder(Long memberId, Long orderId) {
        MemberOrder memberOrder = this.getById(orderId);
        if (memberOrder == null || String.valueOf(orderId).equals(String.valueOf(memberOrder.getMemberId()))) {
            log.error("MemberOrderServiceImpl:: receiver order error , order id = {}", orderId);
            return;
        }
        memberOrder.setStatus(4);
        this.updateById(memberOrder);
    }

    @Override
    public void deleteOrder(Long memberId, Long orderId) {
        MemberOrder memberOrder = this.getById(orderId);
        if (memberOrder == null || String.valueOf(orderId).equals(String.valueOf(memberOrder.getMemberId()))) {
            log.error("MemberOrderServiceImpl:: delete order error , order id = {}", orderId);
            return;
        }
        memberOrder.setDeleted(true);
        memberOrder.setDeletedTime(new Date());
        this.updateById(memberOrder);
    }


    @Override
    public List<MemberOrder> deleteList(Long memberId) {
        return ((MemberOrderMapper) this.getBaseMapper()).deleteList(memberId);
    }

    @Override
    public PageData<OrderResult> findAllMemberOrder(final Integer status, final List<String> createTime, int page, int pageSize) {
        IPage<OrderResult> result = ((MemberOrderMapper) this.getBaseMapper()).findAllMemberOrder(status, createTime, Page.of(page, pageSize));
        return new PageData<OrderResult>().put(result);
    }

    @Override
    public void deliverOrder(Long orderId) {
        MemberOrder memberOrder = this.getById(orderId);
        if (memberOrder == null) {
            log.error("MemberOrderServiceImpl:: deliver order error , order id = {}", orderId);
            return;
        }
        memberOrder.setStatus(3);
        memberOrder.setDeliverTime(DateTime.now().toJdkDate());
        String message = DelayMessage.ofMessage(EventCodeEnum.order_receiver_expire, JsonUtils.obj2json(OrderReceiveDelayedDto.builder().orderId(orderId).build()));
        log.info("MemberOrderServiceImpl:: send order delay message = {}", message);
        rabbitTemplate.convertAndSend(DELAYED_EXCHANGE_NAME, DELAYED_ROUTING_KEY, message,
                correlationData -> {
                    correlationData.getMessageProperties().setDelay(Long.valueOf(1000L * 60 * 60 * 24 * ORDER_RECEIVE_DELAY).intValue());
                    return correlationData;
                });
        this.updateById(memberOrder);
    }

    @Override
    public void receiveOrderByExpireTime(Long orderId) {
        MemberOrder memberOrder = this.getById(orderId);
        if (memberOrder == null) {
            log.error("MemberOrderServiceImpl:: receive order error , order id = {}", orderId);
            return;
        }
        memberOrder.setStatus(4);
        memberOrder.setSuccessTime(DateTime.now().toJdkDate());
        this.updateById(memberOrder);
    }

    @Override
    public PageData<OrderResult> findAllMemberRefundOrder(String nickname, List<String> createTime, int page, int pageSize) {
        IPage<OrderResult> result = ((MemberOrderMapper) this.getBaseMapper()).findAllMemberRefundOrder(nickname, createTime, Page.of(page, pageSize));
        return new PageData<OrderResult>().put(result);
    }

    @Override
    public int auditRefundOrder(Long orderId, Integer status, String reason) {
        MemberOrder memberOrder = this.getById(orderId);
        if (memberOrder == null) {
            log.error("MemberOrderServiceImpl:: audit refund order error , order id = {}", orderId);
            return -1;
        }
        if (memberOrder.getRefundStatus() != 1) {
            log.error("MemberOrderServiceImpl:: audit refund order error , order id = {}", orderId);
            return -2;
        }
        memberOrder.setRefundAuditReason(reason);
        memberOrder.setRefundAuditTime(DateTime.now().toJdkDate());
        if (status == 0) {
            memberOrder.setRefundStatus(3);
            this.updateById(memberOrder);
            return 0;
        }
        /* 1.优惠券返还 */
        if (memberOrder.getUserCouponId() != null) {
            memberCouponService.updateUserCouponUsed(memberOrder.getUserCouponId(), false);
        }
        /* TODO 3.商品库存返还 */
        memberOrder.setRefundStatus(2);
        this.updateById(memberOrder);
        return 0;
    }


    private Long checkCoupon(Long couponId, final Long memberId) {
        if (couponId == null) {
            return null;
        }
        MemberCoupon memberCoupon = memberCouponService.getById(couponId);
        if (memberCoupon == null || !memberCoupon.getMemberId().toString().equals(memberId.toString())) {
            return -1L;
        }
        if (memberCoupon.isUsed()) {
            return -2L;
        }
        return couponId;
    }

    /**
     * 功能描述: <br>
     * 〈保存购物车订单〉
     *
     * @param memberId
     * @param openId    微信openId
     * @param addressId 收货地址ID
     * @param couponId  优惠券ID
     * @param integral
     * @param remarks
     * @param waresIds
     * @return:java.lang.String
     * @since: 1.0.0
     * @Author:
     * @Date: 2023/4/16 15:58
     */
    private String saveCartWaresOrder(final Long memberId, final String openId, final Long addressId, final Long couponId, final String remarks, List<Long> waresIds) {
        List<CartResult> cartResult = memberWaresCartService.cartList(memberId, waresIds);
        if (CollectionUtils.isEmpty(cartResult)) {
            log.error("MemberOrderServiceImpl:: user id = {} , cart ids empty , wares ids = {}", memberId, cartResult);
            return CART_EMPTY;
        }
        boolean waresStatus = cartResult.stream().anyMatch(item -> item.isDeleted() || item.getStatus() == 0);
        if (waresStatus) {
            return ORDER_WARES_EXPIRE;
        }
        Map<Long, Integer> inventoryMap = Maps.newHashMap();
        String redisInventoryKey = null;
        boolean canCreate = true;
        for (CartResult waresCart : cartResult) {
            redisInventoryKey = RedisCacheConstants.WARES_INVENTORY_REDIS_KEY.concat(String.valueOf(waresCart.getWaresId()));
            String waresInventoryNum = redisTemplate.opsForValue().get(redisInventoryKey);
            if (waresInventoryNum == null || Long.parseLong(waresInventoryNum) < waresCart.getNum()) {
                canCreate = false;
                break;
            }
            Long inventory = redisTemplate.opsForValue().increment(redisInventoryKey, -waresCart.getNum());
            if (inventory == null) {
                canCreate = false;
                break;
            }
            if (inventory < 0) {
                redisTemplate.opsForValue().increment(redisInventoryKey, waresCart.getNum());
                canCreate = false;
                break;
            }
            inventoryMap.put(waresCart.getWaresId(), waresCart.getNum());
        }
        if (!canCreate) {
            log.info("MemberOrderServiceImpl:: wares not enough , wares maps = {}", JsonUtils.mapToJson(inventoryMap));
            return NOT_ENOUGH;
        }
        Long orderId = null;
        try {
            orderId = this.save(memberId, FROM_CART, addressId, couponId, remarks, cartResult);
        } catch (Exception ee) {
            log.error("MemberOrderServiceImpl:: save order error , msg = {}", ee.getMessage());
            for (Long waresId : inventoryMap.keySet()) {
                redisTemplate.opsForValue().increment(RedisCacheConstants.WARES_INVENTORY_REDIS_KEY.concat(String.valueOf(waresId)), inventoryMap.get(waresId));
            }
            return ERROR;
        }
        Member member = memberService.findById(memberId);
        if (member == null) {
            log.error("MemberOrderServiceImpl:: save order error , member id = {}", memberId);
            return ERROR;
        }
        String orderIdString = String.valueOf(orderId);
        /* 发送微信订阅消息 */
        SpringContextUtils.publishEvent(new OrderEvent(this, orderIdString, openId, "已下单",
                cartResult.stream().map(CartResult::getName).collect(Collectors.joining(",")),
                DateUtil.now(), "您的订单已下单，我们会尽快为您送货！"));
        return orderIdString;
    }

    /**
     * 功能描述: <br>
     * 〈保存立即下单订单〉
     *
     * @param memberId
     * @param addressId
     * @param couponId  优惠券ID
     * @param integral
     * @param remarks
     * @param waresId
     * @return:java.lang.String
     * @since: 1.0.0
     * @Author:
     * @Date: 2023/4/16 15:47
     */
    private String saveBuyingOutrightOrder(final Long memberId, final String openId, final Long addressId, final Long couponId, final String remarks, final Long waresId) {
        Wares wares = waresService.getById(waresId);
        if (wares == null || wares.isDeleted() || wares.getStatus() == 0) {
            return ORDER_WARES_EXPIRE;
        }
        String redisInventoryKey = RedisCacheConstants.WARES_INVENTORY_REDIS_KEY.concat(String.valueOf(waresId));
        String waresInventoryNum = redisTemplate.opsForValue().get(redisInventoryKey);
        if (waresInventoryNum == null || Long.parseLong(waresInventoryNum) < 1) {
            return NOT_ENOUGH;
        }
        Long inventory = redisTemplate.opsForValue().increment(redisInventoryKey, -1);
        if (inventory == null) {
            return NOT_ENOUGH;
        }
        if (inventory < 0) {
            redisTemplate.opsForValue().increment(redisInventoryKey, 1);
            return NOT_ENOUGH;
        }
        CartResult cartResult = new CartResult();
        cartResult.setNum(1);
        cartResult.setWaresId(waresId);
        Long orderId = null;
        try {
            orderId = this.save(memberId, FROM_WARES, addressId, couponId, remarks, Lists.newArrayList(cartResult));
        } catch (Exception ee) {
            log.error("MemberOrderServiceImpl:: save buying out order error , user id = {}", memberId);
            redisTemplate.opsForValue().increment(RedisCacheConstants.WARES_INVENTORY_REDIS_KEY.concat(String.valueOf(waresId)), 1);
            return ERROR;
        }

        /* 发送微信订阅消息 */
        String orderIdString = String.valueOf(orderId);
        SpringContextUtils.publishEvent(new OrderEvent(this, orderIdString, openId, "已下单", wares.getName(), DateUtil.now(), "您的订单已下单，我们会尽快为您送货！"));
        return orderIdString;
    }

    /**
     * 功能描述: <br>
     * 〈订单通用的保存方法〉
     *
     * @param memberId
     * @param scene
     * @param addressId
     * @param integral
     * @param remarks
     * @param cartResult
     * @return:java.lang.Long
     * @since: 1.0.0
     * @Author:
     * @Date: 2023/4/16 15:59
     */
    private Long save(final Long memberId, final String scene, Long addressId, final Long couponId, String remarks, List<CartResult> cartResult) {
        MemberOrder memberOrder = new MemberOrder();
        memberOrder.setMemberId(memberId);
        memberOrder.setAddressId(addressId);
        memberOrder.setRemarks(remarks);
        memberOrder.setWaresNum(cartResult.stream().map(CartResult::getNum).mapToInt(x -> x).sum());
        memberOrder.setTotalPrice(0L);
        memberOrder.setStatus(2);
        memberOrder.setUserCouponId(couponId);
        /* 1.保存订单 */
        this.save(memberOrder);
        List<MemberOrderItem> memberOrderItems = Lists.newArrayList();
        Map<Long, Integer> inventoryMap = Maps.newHashMap();
        cartResult.forEach(cart -> {
            MemberOrderItem memberOrderItem = new MemberOrderItem();
            memberOrderItem.setOrderId(memberOrder.getId());
            memberOrderItem.setMemberId(memberId);
            memberOrderItem.setNum(cart.getNum());
            memberOrderItem.setWaresId(cart.getWaresId());
            memberOrderItem.setPrice(0L);
            inventoryMap.put(cart.getWaresId(), cart.getNum());
            memberOrderItems.add(memberOrderItem);
        });
        /* 2.保存订单项 */
        memberOrderItemService.saveBatch(memberOrderItems);
        /* 3.减少库存 */
        waresService.updateWaresInventory(inventoryMap);
        /* 4.删除购物车商品 */
        if (scene.equals(FROM_CART)) {
            memberWaresCartService.deleteCartWares(cartResult.stream().map(CartResult::getWaresId).collect(Collectors.toList()), memberId);
        }
        /* 5.更新优惠券 */
        if (couponId != null) {
            memberCouponService.updateUserCouponUsed(couponId, true);
        }
        /* 6.更新用户积分 */
        if (memberOrder.getTotalPrice() > 0) {
            memberService.updateUserIntegral(memberId, memberOrder.getTotalPrice());
        }
        return memberOrder.getId();
    }
}