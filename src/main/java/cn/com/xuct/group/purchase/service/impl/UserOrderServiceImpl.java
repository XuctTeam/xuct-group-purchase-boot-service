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
import cn.com.xuct.group.purchase.constants.RConstants;
import cn.com.xuct.group.purchase.constants.RedisCacheConstants;
import cn.com.xuct.group.purchase.entity.Good;
import cn.com.xuct.group.purchase.entity.UserAddress;
import cn.com.xuct.group.purchase.entity.UserOrder;
import cn.com.xuct.group.purchase.entity.UserOrderItem;
import cn.com.xuct.group.purchase.mapper.UserOrderMapper;
import cn.com.xuct.group.purchase.service.*;
import cn.com.xuct.group.purchase.utils.JsonUtils;
import cn.com.xuct.group.purchase.vo.result.CartResult;
import cn.com.xuct.group.purchase.vo.result.OrderResult;
import cn.com.xuct.group.purchase.vo.result.OrderSumResult;
import cn.hutool.core.date.DateUtil;
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

import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

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
    private final UserAddressService userAddressService;
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
    public String saveOrder(final Long userId, final String scene, final Long addressId, final Integer integral, final String remarks, List<Long> goodIds) {
        switch (scene) {
            case FROM_CART -> {
                return this.saveCartGoodOrder(userId, addressId, integral, remarks, goodIds);
            }
            case FROM_GOOD -> {
                return this.saveBuyingOutrightOrder(userId, addressId, integral, remarks, goodIds.get(0));
            }
            default -> {
                return RConstants.ORDER_SCENE_ERROR;
            }
        }
    }

    @Override
    public IPage<UserOrder> list(Long userId, Integer status, int pageNo, int pageSize) {
        return ((UserOrderMapper) this.getBaseMapper()).list(userId, status, Page.of(pageNo, pageSize));
    }

    @Override
    public OrderResult getDetail(Long userId, Long orderId) {
        UserOrder order = ((UserOrderMapper) this.getBaseMapper()).getOrderDetail(userId, orderId);
        if (order == null) {
            return null;
        }
        OrderResult result = new OrderResult();
        BeanUtils.copyProperties(order, result);
        order.setItems(order.getItems());
        UserAddress address = userAddressService.getById(order.getAddressId());
        result.setAddress(address);
        return result;
    }

    @Override
    public String refundOrder(final Long userId, final Long orderId, final String type, final String reason, final List<String> images) {
        UserOrder userOrder = this.getById(orderId);
        if (userOrder == null) {
            return RConstants.ORDER_NOT_EXIST;
        }
        if (userOrder.getRefundStatus() != 0) {
            return RConstants.ORDER_ALREADY_REFUND;
        }
        userOrder.setRefundStatus(1);
        userOrder.setRefundType(type);
        userOrder.setRefundReason(reason);
        if (!CollectionUtils.isEmpty(images)) {
            userOrder.setRefundImages(String.join(",", images.toArray(new String[images.size()])));
        }
        this.updateById(userOrder);
        return String.valueOf(RConstants.SUCCESS);
    }

    @Override
    @Transactional
    public String cancelOrder(Long userId, Long orderId) {
        UserOrder userOrder = this.getById(orderId);
        if (userOrder == null) {
            return RConstants.ORDER_NOT_EXIST;
        }
        List<UserOrderItem> userOrderItems = userOrderItemService.find(Column.of("order_id", orderId));
        if (CollectionUtils.isEmpty(userOrderItems)) {
            return RConstants.ERROR;
        }
        List<Good> goodList = goodService.find(Column.in("id", userOrderItems.stream().map(UserOrderItem::getGoodId).collect(Collectors.toList())));
        Date date = DateUtil.date().toJdkDate();
        boolean goodExpire = goodList.stream().anyMatch(x -> x.getEndTime().getTime() <= date.getTime());
        if (goodExpire) {
            return RConstants.ORDER_GOOD_EXPIRE;
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
        return String.valueOf(RConstants.SUCCESS);
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

    /**
     * 功能描述: <br>
     * 〈保存购物车订单〉
     *
     * @param userId
     * @param addressId
     * @param integral
     * @param remarks
     * @param goodIds
     * @return:java.lang.String
     * @since: 1.0.0
     * @Author:
     * @Date: 2023/4/16 15:58
     */
    private String saveCartGoodOrder(final Long userId, final Long addressId, final Integer integral, final String remarks, List<Long> goodIds) {
        List<CartResult> cartResult = userGoodCartService.cartList(userId, goodIds);
        if (CollectionUtils.isEmpty(cartResult)) {
            log.error("UserOrderServiceImpl:: user id = {} , cart ids empty , good ids = {}", userId, cartResult);
            return RConstants.CART_EMPTY;
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
            return RConstants.NOT_ENOUGH;
        }
        Long orderId = null;
        try {
            orderId = this.save(userId, FROM_CART, addressId, integral, remarks, cartResult);
        } catch (Exception ee) {
            log.error("UserOrderServiceImpl:: save order error , msg = {}", ee.getMessage());
            for (Long goodId : inventoryMap.keySet()) {
                redisTemplate.opsForValue().increment(RedisCacheConstants.GOOD_INVENTORY_REDIS_KEY.concat(String.valueOf(goodId)), inventoryMap.get(goodId));
            }
            return RConstants.ERROR;
        }
        return String.valueOf(orderId);
    }

    /**
     * 功能描述: <br>
     * 〈保存立即下单订单〉
     *
     * @param userId
     * @param addressId
     * @param integral
     * @param remarks
     * @param goodId
     * @return:java.lang.String
     * @since: 1.0.0
     * @Author:
     * @Date: 2023/4/16 15:47
     */
    private String saveBuyingOutrightOrder(final Long userId, final Long addressId, Integer integral, final String remarks, final Long goodId) {
        Long inventory = redisTemplate.opsForValue().increment(RedisCacheConstants.GOOD_INVENTORY_REDIS_KEY.concat(String.valueOf(goodId)), -1);
        if (inventory == null || inventory <= 0) {
            redisTemplate.opsForValue().increment(RedisCacheConstants.GOOD_INVENTORY_REDIS_KEY.concat(String.valueOf(goodId)), 1);
            return RConstants.NOT_ENOUGH;
        }
        CartResult cartResult = new CartResult();
        cartResult.setNum(1);
        cartResult.setGoodId(goodId);
        Long orderId = null;
        try {
            orderId = this.save(userId, FROM_GOOD, addressId, integral, remarks, Lists.newArrayList(cartResult));
        } catch (Exception ee) {
            log.error("UserOrderServiceImpl:: save buying out order error , user id = {}", userId);
            redisTemplate.opsForValue().increment(RedisCacheConstants.GOOD_INVENTORY_REDIS_KEY.concat(String.valueOf(goodId)), 1);
            return RConstants.ERROR;
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
    private Long save(final Long userId, final String scene, Long addressId, Integer integral, String remarks, List<CartResult> cartResult) {
        UserOrder userOrder = new UserOrder();
        userOrder.setUserId(userId);
        userOrder.setAddressId(addressId);
        userOrder.setIntegral(integral);
        userOrder.setRemarks(remarks);
        userOrder.setGoodNum(cartResult.stream().map(CartResult::getNum).mapToInt(x -> x).sum());
        userOrder.setTotalPrice(0L);
        userOrder.setStatus(2);
        //1.保存订单
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
        //2. 保存订单项
        userOrderItemService.saveBatch(userOrderItems);
        //3. 减少库存
        goodService.updateGoodInventory(inventoryMap);
        //4. 删除购物车商品
        if (scene.equals(FROM_CART)) {
            userGoodCartService.deleteCartGood(cartResult.stream().map(CartResult::getGoodId).collect(Collectors.toList()), userId);
        }
        //5. 更新用户积分
        if (integral > 0) {
            userService.updateUserIntegral(userId, -integral);
        }
        return userOrder.getId();
    }
}