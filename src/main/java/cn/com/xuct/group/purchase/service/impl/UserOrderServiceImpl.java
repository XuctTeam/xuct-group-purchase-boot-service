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
import cn.com.xuct.group.purchase.constants.OrderResultConstants;
import cn.com.xuct.group.purchase.constants.RedisCacheConstants;
import cn.com.xuct.group.purchase.entity.UserOrder;
import cn.com.xuct.group.purchase.entity.UserOrderItem;
import cn.com.xuct.group.purchase.mapper.UserOrderMapper;
import cn.com.xuct.group.purchase.service.*;
import cn.com.xuct.group.purchase.utils.JsonUtils;
import cn.com.xuct.group.purchase.vo.result.CartResult;
import com.google.common.collect.Maps;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.assertj.core.util.Lists;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;

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

    private final StringRedisTemplate redisTemplate;

    @Override
    @Transactional(rollbackFor = Exception.class)
    public String saveOrder(Long userId, Long addressId, Integer integral, String remarks, List<Long> goodIds) {
        List<CartResult> cartResult = userGoodCartService.cartList(userId, goodIds);
        if (CollectionUtils.isEmpty(cartResult)) {
            log.error("UserOrderServiceImpl:: user id = {} , cart ids empty , good ids = {}", userId, cartResult);
            return OrderResultConstants.CART_EMPTY;
        }
        Map<Long, Integer> stokMap = Maps.newHashMap();
        boolean canCreate = true;
        for (CartResult goodCart : cartResult) {
            Long stock = redisTemplate.opsForValue().increment(RedisCacheConstants.GOOD_INVENTORY_REDIS_KEY.concat(String.valueOf(goodCart.getGoodId())), -goodCart.getNum());
            stokMap.put(goodCart.getGoodId(), goodCart.getNum());
            if (stock == null || stock <= 0) {
                canCreate = false;
                break;
            }
        }
        if (!canCreate) {
            log.info("UserOrderServiceImpl:: good not enough , good maps = {}", JsonUtils.mapToJson(stokMap));
            for (Long goodId : stokMap.keySet()) {
                redisTemplate.opsForValue().increment(RedisCacheConstants.GOOD_INVENTORY_REDIS_KEY.concat(String.valueOf(goodId)), stokMap.get(goodId));
            }
            return OrderResultConstants.NOT_ENOUGH;
        }
        Long orderId = null;
        try {
            orderId = this.save(userId, addressId, integral, remarks, cartResult);
        } catch (Exception ee) {
            log.error("UserOrderServiceImpl:: save order error , msg = {}", ee.getMessage());
            for (Long goodId : stokMap.keySet()) {
                redisTemplate.opsForValue().increment(RedisCacheConstants.GOOD_INVENTORY_REDIS_KEY.concat(String.valueOf(goodId)), stokMap.get(goodId));
            }
            return OrderResultConstants.ERROR;
        }
        return String.valueOf(orderId);
    }

    private Long save(Long userId, Long addressId, Integer integral, String remarks, List<CartResult> cartResult) {
        UserOrder userOrder = new UserOrder();
        userOrder.setUserId(userId);
        userOrder.setAddressId(addressId);
        userOrder.setIntegral(integral);
        userOrder.setRemarks(remarks);
        userOrder.setGoodNum(cartResult.stream().map(CartResult::getNum).mapToInt(x -> x).sum());
        userOrder.setTotalPrice(0L);
        //1.保存订单
        this.save(userOrder);

        List<UserOrderItem> userOrderItems = Lists.newArrayList();
        Map<Long, Integer> stokMap = Maps.newHashMap();
        cartResult.forEach(cart -> {
            UserOrderItem userOrderItem = new UserOrderItem();
            userOrderItem.setOrderId(userOrder.getId());
            userOrderItem.setUserId(userId);
            userOrderItem.setNum(cart.getNum());
            userOrderItem.setGoodId(cart.getGoodId());
            userOrderItem.setPrice(0L);
            stokMap.put(cart.getGoodId(), cart.getInventory() - cart.getNum());
            userOrderItems.add(userOrderItem);
        });
        //2. 保存订单项
        userOrderItemService.saveBatch(userOrderItems);
        //3. 减少库存
        goodService.updateStokByGoodIds(stokMap);
        //4. 删除购物车商品
        userGoodCartService.deleteCartGood(cartResult.stream().map(CartResult::getGoodId).collect(Collectors.toList()), userId);
        //5. 更新用户积分
        if (integral > 0) {
            userService.updateUserIntegral(userId, -integral);
        }
        return userOrder.getId();
    }
}