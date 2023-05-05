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
import cn.com.xuct.group.purchase.mapper.UserOrderMapper;
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
public class UserOrderServiceImpl extends BaseServiceImpl<UserOrderMapper, UserOrder> implements UserOrderService {
    private final UserGoodCartService userGoodCartService;
    private final UserOrderItemService userOrderItemService;
    private final GoodService goodService;
    private final UserService userService;
    private final UserGoodEvaluateService userGoodEvaluateService;
    private final UserCouponService userCouponService;

    private final StringRedisTemplate redisTemplate;

    @Override
    public OrderSumResult sumCount(Long userId) {
        OrderSumResult result = ((UserOrderMapper) this.getBaseMapper()).sumCount(userId);
        result.setToBeEvaluationCount(userOrderItemService.countEvaluation(userId));
        return result;
    }

    @Override
    public List<CartResult> getConfirmOrderDetail(Long userId, String scene, List<Long> gids) {
        if (FROM_CART.equals(scene)) {
            return userGoodCartService.cartList(userId, gids);
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
    public String saveOrder(final Long userId, final String scene, final Long addressId, Long couponId, Integer integral, final String remarks, List<Long> goodIds) {
        log.info("UserOrderServiceImpl:: save order , user id = {} , address id = {} , couponId id = {} , integral = {} , goodIds = {}", userId, addressId, couponId, integral, JsonUtils.obj2json(goodIds));
        //全部积分
        integral = this.checkIntegral(integral, userId);
        switch (integral) {
            case -1 -> {
                return USER_NOT_EXIST;
            }
            case -2 -> {
                return USER_INTEGRAL_NOT_ENOUGH;
            }
        }
        couponId = this.checkCoupon(couponId, userId);
        if (couponId != null && (couponId == -1L || couponId == -2L)) {
            return COUPON_NOT_EXIST;
        }
        switch (scene) {
            case FROM_CART -> {
                return this.saveCartGoodOrder(userId, addressId, couponId, integral, remarks, goodIds);
            }
            case FROM_GOOD -> {
                return this.saveBuyingOutrightOrder(userId, addressId, couponId, integral, remarks, goodIds.get(0));
            }
            default -> {
                return ORDER_SCENE_ERROR;
            }
        }
    }

    @Override
    public IPage<UserOrder> search(final Long userId, int pageNo, int pageSize, final int refund, final String word) {
        String wordId = word;
        String wordGoodName = null;
        if (Validator.hasChinese(word) || Validator.isWord(word)) {
            wordGoodName = word;
            wordId = null;
        }
        return ((UserOrderMapper) this.getBaseMapper()).search(userId, Page.of(pageNo, pageSize), refund, wordId, wordGoodName);
    }

    @Override
    public IPage<UserOrder> list(Long userId, Integer status, int pageNo, int pageSize, final Integer refundStatus) {
        return ((UserOrderMapper) this.getBaseMapper()).list(userId, status, Page.of(pageNo, pageSize), refundStatus);
    }

    @Override
    public OrderResult getDetail(Long userId, Long orderId) {
        OrderResult order = ((UserOrderMapper) this.getBaseMapper()).getOrderDetail(orderId);
        if (order == null) {
            return null;
        }
        if (order.getUserCouponId() == null) {
            return order;
        }
        order.setCoupon(userCouponService.getUserCoupon(userId, order.getUserCouponId()));
        return order;
    }

    @Override
    public String refundOrder(final Long userId, final Long orderId, final String type, final String reason, final List<String> images) {
        UserOrder userOrder = this.getById(orderId);
        if (userOrder == null) {
            return ORDER_NOT_EXIST;
        }
        if (userOrder.getRefundStatus() != 0) {
            return ORDER_ALREADY_REFUND;
        }
        userOrder.setRefundStatus(1);
        userOrder.setRefundType(type);
        userOrder.setRefundReason(reason);
        userOrder.setRefundTime(new Date());
        if (!CollectionUtils.isEmpty(images)) {
            userOrder.setRefundImages(String.join(",", images.toArray(new String[0])));
        }
        this.updateById(userOrder);
        return String.valueOf(SUCCESS);
    }

    @Override
    public String cancelRefundOrder(Long userId, Long orderId) {
        UserOrder userOrder = this.getById(orderId);
        if (userOrder == null) {
            return ORDER_NOT_EXIST;
        }
        if (userOrder.getRefundStatus() != 1) {
            return ORDER_NOT_REFUND;
        }
        userOrder.setRefundStatus(3);
        this.updateById(userOrder);
        return String.valueOf(SUCCESS);
    }

    @Override
    @Transactional
    public String cancelOrder(Long userId, Long orderId) {
        UserOrder userOrder = this.getById(orderId);
        if (userOrder == null) {
            return ORDER_NOT_EXIST;
        }
        List<UserOrderItem> userOrderItems = userOrderItemService.find(Column.of("order_id", orderId));
        if (CollectionUtils.isEmpty(userOrderItems)) {
            return ERROR;
        }
        List<Good> goodList = goodService.find(Column.in("id", userOrderItems.stream().map(UserOrderItem::getGoodId).collect(Collectors.toList())));
        Date date = DateUtil.date().toJdkDate();
        boolean goodExpire = goodList.stream().anyMatch(x -> x.getEndTime().getTime() <= date.getTime());
        if (goodExpire) {
            return ORDER_GOOD_EXPIRE;
        }
        Map<Long, Integer> inventoryMap = userOrderItems.stream().collect(Collectors.toMap(UserOrderItem::getUserId, UserOrderItem::getNum));
        /* 1.更新商品库存 */
        for (Long gid : inventoryMap.keySet()) {
            redisTemplate.opsForValue().increment(RedisCacheConstants.GOOD_INVENTORY_REDIS_KEY.concat(String.valueOf(gid)), inventoryMap.get(gid));
        }
        goodList.forEach(g -> {
            g.setInventory(g.getInventory() + inventoryMap.get(g.getId()));
        });
        goodService.updateBatchById(goodList);
        /* 2.更新订单状态 */
        userOrder.setDeleted(true);
        this.updateById(userOrder);
        return String.valueOf(SUCCESS);
    }

    @Override
    public void rushOrder(Long userId, Long orderId) {
        UserOrder userOrder = this.getById(orderId);
        if (userOrder == null || String.valueOf(orderId).equals(String.valueOf(userOrder.getUserId()))) {
            log.error("UserOrderServiceImpl:: rush order error , order id = {}", orderId);
            return;
        }
        userOrder.setRush(true);
        this.updateById(userOrder);
    }

    @Override
    public void receiveOrder(Long userId, Long orderId) {
        UserOrder userOrder = this.getById(orderId);
        if (userOrder == null || String.valueOf(orderId).equals(String.valueOf(userOrder.getUserId()))) {
            log.error("UserOrderServiceImpl:: receiver order error , order id = {}", orderId);
            return;
        }
        userOrder.setStatus(4);
        this.updateById(userOrder);
    }

    @Override
    public void deleteOrder(Long userId, Long orderId) {
        UserOrder userOrder = this.getById(orderId);
        if (userOrder == null || String.valueOf(orderId).equals(String.valueOf(userOrder.getUserId()))) {
            log.error("UserOrderServiceImpl:: delete order error , order id = {}", orderId);
            return;
        }
        userOrder.setDeleted(true);
        userOrder.setDeletedTime(new Date());
        this.updateById(userOrder);
    }

    @Override
    public List<UserOrderItem> evaluateList(Long userId) {
        return userOrderItemService.queryEvaluateByUserId(userId);
    }

    @Override
    @Transactional
    public void evaluateGood(Long userId, Long orderItemId, String rate, String evaluateImages, String remarks) {
        UserOrderItem item = userOrderItemService.getById(orderItemId);
        if (item == null) {
            log.error("UserOrderServiceImpl:: save evaluate error , order item id = {}", orderItemId);
            return;
        }
        UserGoodEvaluate evaluate = new UserGoodEvaluate();
        evaluate.setUserId(userId);
        evaluate.setOrderItemId(orderItemId);
        evaluate.setGoodId(item.getGoodId());
        evaluate.setRate(rate);
        if (StringUtils.hasLength(evaluateImages)) {
            evaluate.setEvaluateImages(evaluateImages);
        }
        if (StringUtils.hasLength(remarks)) {
            evaluate.setRemarks(remarks);
        }
        userGoodEvaluateService.save(evaluate);
        item.setEvaluation(true);
        userOrderItemService.updateById(item);
    }

    private Integer checkIntegral(Integer integral, final Long userId) {
        if (integral == 0) {
            return integral;
        }
        User user = userService.getById(userId);
        if (user == null) {
            return -1;
        }
        if (integral != -999 && integral > user.getIntegral()) {
            return -2;
        }
        if (integral == -999) {
            return user.getIntegral().intValue();
        }
        return integral;
    }


    private Long checkCoupon(Long couponId, final Long userId) {
        if (couponId == null) {
            return null;
        }
        UserCoupon userCoupon = userCouponService.getById(couponId);
        if (userCoupon == null || !userCoupon.getUserId().toString().equals(userId.toString())) {
            return -1L;
        }
        if (userCoupon.isUsed()) {
            return -2L;
        }
        return couponId;
    }

    /**
     * 功能描述: <br>
     * 〈保存购物车订单〉
     *
     * @param userId
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
    private String saveCartGoodOrder(final Long userId, final Long addressId, final Long couponId, final Integer integral, final String remarks, List<Long> goodIds) {
        List<CartResult> cartResult = userGoodCartService.cartList(userId, goodIds);
        if (CollectionUtils.isEmpty(cartResult)) {
            log.error("UserOrderServiceImpl:: user id = {} , cart ids empty , good ids = {}", userId, cartResult);
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
            orderId = this.save(userId, FROM_CART, addressId, couponId, integral, remarks, cartResult);
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
     * @param userId
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
    private String saveBuyingOutrightOrder(final Long userId, final Long addressId, final Long couponId, Integer integral, final String remarks, final Long goodId) {
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
            orderId = this.save(userId, FROM_GOOD, addressId, couponId, integral, remarks, Lists.newArrayList(cartResult));
        } catch (Exception ee) {
            log.error("UserOrderServiceImpl:: save buying out order error , user id = {}", userId);
            redisTemplate.opsForValue().increment(RedisCacheConstants.GOOD_INVENTORY_REDIS_KEY.concat(String.valueOf(goodId)), 1);
            return ERROR;
        }
        return String.valueOf(orderId);
    }

    /**
     * 功能描述: <br>
     * 〈订单通用的保存方法〉
     *
     * @param userId
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
    private Long save(final Long userId, final String scene, Long addressId, final Long couponId, Integer integral, String remarks, List<CartResult> cartResult) {
        UserOrder userOrder = new UserOrder();
        userOrder.setUserId(userId);
        userOrder.setAddressId(addressId);
        userOrder.setIntegral(integral);
        userOrder.setRemarks(remarks);
        userOrder.setGoodNum(cartResult.stream().map(CartResult::getNum).mapToInt(x -> x).sum());
        userOrder.setTotalPrice(0L);
        userOrder.setStatus(2);
        userOrder.setUserCouponId(couponId);
        /*1. 保存订单*/
        this.save(userOrder);
        List<UserOrderItem> userOrderItems = Lists.newArrayList();
        Map<Long, Integer> inventoryMap = Maps.newHashMap();
        cartResult.forEach(cart -> {
            UserOrderItem userOrderItem = new UserOrderItem();
            userOrderItem.setOrderId(userOrder.getId());
            userOrderItem.setUserId(userId);
            userOrderItem.setNum(cart.getNum());
            userOrderItem.setGoodId(cart.getGoodId());
            userOrderItem.setPrice(0L);
            inventoryMap.put(cart.getGoodId(), cart.getNum());
            userOrderItems.add(userOrderItem);
        });
        /*2. 保存订单项*/
        userOrderItemService.saveBatch(userOrderItems);
        /*3. 减少库存*/
        goodService.updateGoodInventory(inventoryMap);
        /*4. 删除购物车商品*/
        if (scene.equals(FROM_CART)) {
            userGoodCartService.deleteCartGood(cartResult.stream().map(CartResult::getGoodId).collect(Collectors.toList()), userId);
        }
        /*5. 更新优惠券 */
        if (couponId != null) {
            userCouponService.updateUserCouponUsed(couponId);
        }
        /*6. 更新用户积分*/
        if (integral > 0) {
            userService.updateUserIntegral(userId, -integral);
        }
        return userOrder.getId();
    }
}