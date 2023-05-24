/**
 * Copyright (C), 2015-2023, 263企业通信
 * FileName: MessageEventReceiver
 * Author:   Derek Xu
 * Date:     2023/5/17 8:34
 * Description:
 * History:
 * <author>          <time>          <version>          <desc>
 * 作者姓名           修改时间           版本号              描述
 */
package cn.com.xuct.group.purchase.mq;

import cn.com.xuct.group.purchase.entity.MemberOrder;
import cn.com.xuct.group.purchase.service.CouponService;
import cn.com.xuct.group.purchase.service.MemberOrderService;
import cn.com.xuct.group.purchase.service.WaresService;
import cn.com.xuct.group.purchase.utils.JsonUtils;
import cn.com.xuct.group.purchase.vo.dto.CouponDelayedDto;
import cn.com.xuct.group.purchase.vo.dto.OrderReceiveDelayedDto;
import cn.com.xuct.group.purchase.vo.dto.WaresDelayedDto;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;

/**
 * 〈一句话功能简述〉<br>
 * 〈〉
 *
 * @author Derek Xu
 * @create 2023/5/17
 * @since 1.0.0
 */
@Slf4j
@Component
@RequiredArgsConstructor
public class MessageEventReceiver {


    private final WaresService waresService;

    private final CouponService couponService;

    private final MemberOrderService memberOrderService;

    /**
     * 监听过期消息
     *
     * @param event
     */
    @EventListener
    public void receiveMessage(MessageEvent event) {
        switch (event.getCode()) {
            case wares_expire -> this.waresExpire(event.getData());
            case coupon_expire -> this.couponExpire(event.getData());
            case order_receiver_expire -> this.receiveOrderExpire(event.getData());
            default -> log.error("MessageEventReceiver:: message not support , code = {}", event.getCode());
        }
    }

    /**
     * 商品过期
     *
     * @param data
     * @return:
     * @since: 1.0.0
     * @Author:Derek xu
     * @Date: 2023/5/18 9:30
     */
    private <T> void waresExpire(T data) {

        WaresDelayedDto waresDelayedDto = JsonUtils.json2pojo(String.valueOf(data), WaresDelayedDto.class);
        if (waresDelayedDto == null) {
            return;
        }
        waresService.waresExpire(waresDelayedDto.getWaresId(), waresDelayedDto.getVersion());
    }

    /**
     * 优惠券过期
     *
     * @param data
     * @return:
     * @since: 1.0.0
     * @Author:Derek xu
     * @Date: 2023/5/18 9:30
     */
    private <T> void couponExpire(T data) {
        CouponDelayedDto couponDelayedDto = JsonUtils.json2pojo(String.valueOf(data), CouponDelayedDto.class);
        if (couponDelayedDto == null) {
            return;
        }
        couponService.couponExpire(couponDelayedDto.getCouponId(), couponDelayedDto.getVersion());
    }

    /**
     * 收货时间过期
     *
     * @param data
     * @return:
     * @since: 1.0.0
     * @Author:Derek xu
     * @Date: 2023/5/24 9:19
     */
    private <T> void receiveOrderExpire(T data) {
        log.info("MessageEventReceiver:: receiveMessage , data = {}", data);
        OrderReceiveDelayedDto orderReceiveDelayedDto = JsonUtils.json2pojo(String.valueOf(data), OrderReceiveDelayedDto.class);
        if (orderReceiveDelayedDto == null) {
            return;
        }
        memberOrderService.receiveOrderByExpireTime(orderReceiveDelayedDto.getOrderId());
    }
}