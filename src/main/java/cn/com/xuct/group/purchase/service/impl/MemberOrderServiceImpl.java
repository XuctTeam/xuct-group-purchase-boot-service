/**
 * Copyright (C), 2015-2023, 263企业通信
 * FileName: UserOrderServiceImpl
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
import cn.com.xuct.group.purchase.constants.RedisCacheConstants;
import cn.com.xuct.group.purchase.entity.*;
import cn.com.xuct.group.purchase.mapper.MemberOrderMapper;
import cn.com.xuct.group.purchase.service.*;
import cn.com.xuct.group.purchase.utils.JsonUtils;
import cn.com.xuct.group.purchase.vo.result.CartResult;
import cn.com.xuct.group.purchase.vo.result.OrderResult;
import cn.com.xuct.group.purchase.vo.result.OrderSumResult;
import cn.hutool.core.date.DateUtil;
import cn.hutool.core.lang.Validator;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.google.common.collect.Maps;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.assertj.core.util.Lists;
import org.springframework.beans.BeanUtils;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;
import org.springframework.util.StringUtils;

import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

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
    private final MemberGoodCartService memberGoodCartService;
    private final MemberOrderItemService memberOrderItemService;
    private final GoodService goodService;
    private final MemberService memberService;
    private final MemberGoodEvaluateService memberGoodEvaluateService;
    private final MemberCouponService memberCouponService;

    private final StringRedisTemplate redisTemplate;

    @Override
    public OrderSumResult sumCount(Long memberId) {
        OrderSumResult result = ((MemberOrderMapper) this.getBaseMapper()).sumCount(memberId);
        result.setToBeEvaluationCount(memberOrderItemService.countEvaluation(memberId));
        return result;
    }

    @Override
    public List<CartResult> getConfirmOrderDetail(Long memberId, String scene, List<Long> gids) {
        if (FROM_CART.equals(scene)) {
            return memberGoodCartService.cartList(memberId, gids);
        }
        CartResult cartResult = new CartResult();
        Good good = goodService.getById(gids.get(0));
        if (good == null) {
            return Lists.newArrayList();
        }
        BeanUtils.copyProperties(good, cartResult);
        cartResult.setGoodId(good.getId());
        cartResult.setNum(1);
        return Lists.newArrayList(cartResult);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public String saveOrder(final Long memberId, final String scene, final Long addressId, Long couponId, Integer integral, final String remarks, List<Long> goodIds) {
        log.info("UserOrderServiceImpl:: save order , member id = {} , address id = {} , couponId id = {} , integral = {} , goodIds = {}", memberId, addressId, couponId, integral, JsonUtils.obj2json(goodIds));
        //全部积分
        integral = this.checkIntegral(integral, memberId);
        switch (integral) {
            case -1 -> {
                return USER_NOT_EXIST;
            }
            case -2 -> {
                return USER_INTEGRAL_NOT_ENOUGH;
            }
        }
        couponId = this.checkCoupon(couponId, memberId);
        if (couponId != null && (couponId == -1L || couponId == -2L)) {
            return COUPON_NOT_EXIST;
        }
        switch (scene) {
            case FROM_CART -> {
                return this.saveCartGoodOrder(memberId, addressId, couponId, integral, remarks, goodIds);
            }
            case FROM_GOOD -> {
                return this.saveBuyingOutrightOrder(memberId, addressId, couponId, integral, remarks, goodIds.get(0));
            }
            default -> {
                return ORDER_SCENE_ERROR;
            }
        }
    }

    @Override
    public IPage<MemberOrder> search(final Long memberId, int pageNo, int pageSize, final int refund, final String word) {
        String wordId = word;
        String wordGoodName = null;
        if (Validator.hasChinese(word) || Validator.isWord(word)) {
            wordGoodName = word;
            wordId = null;
        }
        return ((MemberOrderMapper) this.getBaseMapper()).search(memberId, Page.of(pageNo, pageSize), refund, wordId, wordGoodName);
    }

    @Override
    public IPage<MemberOrder> list(Long memberId, Integer status, int pageNo, int pageSize, final Integer refundStatus) {
        return ((MemberOrderMapper) this.getBaseMapper()).list(memberId, status, Page.of(pageNo, pageSize), refundStatus);
    }

    @Override
    public OrderResult getDetail(Long memberId, Long orderId) {
        OrderResult order = ((MemberOrderMapper) this.getBaseMapper()).getOrderDetail(orderId);
        if (order == null) {
            return null;
        }
        if (order.getUserCouponId() == null) {
            return order;
        }
        order.setCoupon(memberCouponService.getUserCoupon(memberId, order.getUserCouponId()));
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
        List<Good> goodList = goodService.find(Column.in("id", memberOrderItems.stream().map(MemberOrderItem::getGoodId).collect(Collectors.toList())));
        Date date = DateUtil.date().toJdkDate();
        boolean goodExpire = goodList.stream().anyMatch(x -> x.getEndTime().getTime() <= date.getTime());
        if (goodExpire) {
            return ORDER_GOOD_EXPIRE;
        }
        Map<Long, Integer> inventoryMap = memberOrderItems.stream().collect(Collectors.toMap(MemberOrderItem::getMemberId, MemberOrderItem::getNum));
        /* 1.更新商品库存 */
        for (Long gid : inventoryMap.keySet()) {
            redisTemplate.opsForValue().increment(RedisCacheConstants.GOOD_INVENTORY_REDIS_KEY.concat(String.valueOf(gid)), inventoryMap.get(gid));
        }
        goodList.forEach(g -> {
            g.setInventory(g.getInventory() + inventoryMap.get(g.getId()));
        });
        goodService.updateBatchById(goodList);
        /* 2.更新订单状态 */
        memberOrder.setDeleted(true);
        this.updateById(memberOrder);
        return String.valueOf(SUCCESS);
    }

    @Override
    public void rushOrder(Long memberId, Long orderId) {
        MemberOrder memberOrder = this.getById(orderId);
        if (memberOrder == null || String.valueOf(orderId).equals(String.valueOf(memberOrder.getMemberId()))) {
            log.error("UserOrderServiceImpl:: rush order error , order id = {}", orderId);
            return;
        }
        memberOrder.setRush(true);
        this.updateById(memberOrder);
    }

    @Override
    public void receiveOrder(Long memberId, Long orderId) {
        MemberOrder memberOrder = this.getById(orderId);
        if (memberOrder == null || String.valueOf(orderId).equals(String.valueOf(memberOrder.getMemberId()))) {
            log.error("UserOrderServiceImpl:: receiver order error , order id = {}", orderId);
            return;
        }
        memberOrder.setStatus(4);
        this.updateById(memberOrder);
    }

    @Override
    public void deleteOrder(Long memberId, Long orderId) {
        MemberOrder memberOrder = this.getById(orderId);
        if (memberOrder == null || String.valueOf(orderId).equals(String.valueOf(memberOrder.getMemberId()))) {
            log.error("UserOrderServiceImpl:: delete order error , order id = {}", orderId);
            return;
        }
        memberOrder.setDeleted(true);
        memberOrder.setDeletedTime(new Date());
        this.updateById(memberOrder);
    }

    @Override
    public List<MemberOrderItem> evaluateList(Long memberId) {
        return memberOrderItemService.queryEvaluateByMemberId(memberId);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void evaluateGood(Long memberId, Long orderItemId, String rate, String evaluateImages, String remarks) {
        MemberOrderItem item = memberOrderItemService.getById(orderItemId);
        if (item == null) {
            log.error("UserOrderServiceImpl:: save evaluate error , order item id = {}", orderItemId);
            return;
        }
        MemberGoodEvaluate evaluate = new MemberGoodEvaluate();
        evaluate.setMemberId(memberId);
        evaluate.setOrderItemId(orderItemId);
        evaluate.setGoodId(item.getGoodId());
        evaluate.setRate(rate);
        if (StringUtils.hasLength(evaluateImages)) {
            evaluate.setEvaluateImages(evaluateImages);
        }
        if (StringUtils.hasLength(remarks)) {
            evaluate.setRemarks(remarks);
        }
        memberGoodEvaluateService.save(evaluate);
        item.setEvaluation(true);
        memberOrderItemService.updateById(item);
    }

    @Override
    public List<MemberOrder> deleteList(Long memberId) {
        return ((MemberOrderMapper) this.getBaseMapper()).deleteList(memberId);
    }

    private Integer checkIntegral(Integer integral, final Long memberId) {
        if (integral == 0) {
            return integral;
        }
        Member member = memberService.getById(memberId);
        if (member == null) {
            return -1;
        }
        if (integral != -999 && integral > member.getIntegral()) {
            return -2;
        }
        if (integral == -999) {
            return member.getIntegral().intValue();
        }
        return integral;
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
     * @param addressId 收货地址ID
     * @param couponId  优惠券ID
     * @param integral
     * @param remarks
     * @param goodIds
     * @return:java.lang.String
     * @since: 1.0.0
     * @Author:
     * @Date: 2023/4/16 15:58
     */
    private String saveCartGoodOrder(final Long memberId, final Long addressId, final Long couponId, final Integer integral, final String remarks, List<Long> goodIds) {
        List<CartResult> cartResult = memberGoodCartService.cartList(memberId, goodIds);
        if (CollectionUtils.isEmpty(cartResult)) {
            log.error("UserOrderServiceImpl:: user id = {} , cart ids empty , good ids = {}", memberId, cartResult);
            return CART_EMPTY;
        }
        Map<Long, Integer> inventoryMap = Maps.newHashMap();
        boolean canCreate = true;
        for (CartResult goodCart : cartResult) {
            Long inventory = redisTemplate.opsForValue().increment(RedisCacheConstants.GOOD_INVENTORY_REDIS_KEY.concat(String.valueOf(goodCart.getGoodId())), -goodCart.getNum());
            inventoryMap.put(goodCart.getGoodId(), goodCart.getNum());
            if (inventory == null || inventory <= 0) {
                canCreate = false;
                break;
            }
        }
        if (!canCreate) {
            log.info("UserOrderServiceImpl:: good not enough , good maps = {}", JsonUtils.mapToJson(inventoryMap));
            for (Long goodId : inventoryMap.keySet()) {
                redisTemplate.opsForValue().increment(RedisCacheConstants.GOOD_INVENTORY_REDIS_KEY.concat(String.valueOf(goodId)), inventoryMap.get(goodId));
            }
            return NOT_ENOUGH;
        }
        Long orderId = null;
        try {
            orderId = this.save(memberId, FROM_CART, addressId, couponId, integral, remarks, cartResult);
        } catch (Exception ee) {
            log.error("UserOrderServiceImpl:: save order error , msg = {}", ee.getMessage());
            for (Long goodId : inventoryMap.keySet()) {
                redisTemplate.opsForValue().increment(RedisCacheConstants.GOOD_INVENTORY_REDIS_KEY.concat(String.valueOf(goodId)), inventoryMap.get(goodId));
            }
            return ERROR;
        }
        return String.valueOf(orderId);
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
     * @param goodId
     * @return:java.lang.String
     * @since: 1.0.0
     * @Author:
     * @Date: 2023/4/16 15:47
     */
    private String saveBuyingOutrightOrder(final Long memberId, final Long addressId, final Long couponId, Integer integral, final String remarks, final Long goodId) {
        Long inventory = redisTemplate.opsForValue().increment(RedisCacheConstants.GOOD_INVENTORY_REDIS_KEY.concat(String.valueOf(goodId)), -1);
        if (inventory == null || inventory <= 0) {
            redisTemplate.opsForValue().increment(RedisCacheConstants.GOOD_INVENTORY_REDIS_KEY.concat(String.valueOf(goodId)), 1);
            return NOT_ENOUGH;
        }
        CartResult cartResult = new CartResult();
        cartResult.setNum(1);
        cartResult.setGoodId(goodId);
        Long orderId = null;
        try {
            orderId = this.save(memberId, FROM_GOOD, addressId, couponId, integral, remarks, Lists.newArrayList(cartResult));
        } catch (Exception ee) {
            log.error("UserOrderServiceImpl:: save buying out order error , user id = {}", memberId);
            redisTemplate.opsForValue().increment(RedisCacheConstants.GOOD_INVENTORY_REDIS_KEY.concat(String.valueOf(goodId)), 1);
            return ERROR;
        }
        return String.valueOf(orderId);
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
    private Long save(final Long memberId, final String scene, Long addressId, final Long couponId, Integer integral, String remarks, List<CartResult> cartResult) {
        MemberOrder memberOrder = new MemberOrder();
        memberOrder.setMemberId(memberId);
        memberOrder.setAddressId(addressId);
        memberOrder.setIntegral(integral);
        memberOrder.setRemarks(remarks);
        memberOrder.setGoodNum(cartResult.stream().map(CartResult::getNum).mapToInt(x -> x).sum());
        memberOrder.setTotalPrice(0L);
        memberOrder.setStatus(2);
        memberOrder.setUserCouponId(couponId);
        /*1. 保存订单*/
        this.save(memberOrder);
        List<MemberOrderItem> memberOrderItems = Lists.newArrayList();
        Map<Long, Integer> inventoryMap = Maps.newHashMap();
        cartResult.forEach(cart -> {
            MemberOrderItem memberOrderItem = new MemberOrderItem();
            memberOrderItem.setOrderId(memberOrder.getId());
            memberOrderItem.setMemberId(memberId);
            memberOrderItem.setNum(cart.getNum());
            memberOrderItem.setGoodId(cart.getGoodId());
            memberOrderItem.setPrice(0L);
            inventoryMap.put(cart.getGoodId(), cart.getNum());
            memberOrderItems.add(memberOrderItem);
        });
        /*2. 保存订单项*/
        memberOrderItemService.saveBatch(memberOrderItems);
        /*3. 减少库存*/
        goodService.updateGoodInventory(inventoryMap);
        /*4. 删除购物车商品*/
        if (scene.equals(FROM_CART)) {
            memberGoodCartService.deleteCartGood(cartResult.stream().map(CartResult::getGoodId).collect(Collectors.toList()), memberId);
        }
        /*5. 更新优惠券 */
        if (couponId != null) {
            memberCouponService.updateUserCouponUsed(couponId);
        }
        /*6. 更新用户积分*/
        if (integral > 0) {
            memberService.updateUserIntegral(memberId, -integral);
        }
        return memberOrder.getId();
    }
}