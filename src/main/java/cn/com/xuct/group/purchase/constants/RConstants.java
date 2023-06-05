/**
 * Copyright (C), 2021-2021, 263
 * FileName: RConstant
 * Author:   Derek xu
 * Date:     2021/6/17 20:02
 * Description:
 * History:
 * <author>          <time>          <version>          <desc>
 * 作者姓名           修改时间           版本号              描述
 */
package cn.com.xuct.group.purchase.constants;

/**
 * 〈一句话功能简述〉<br>
 * 〈〉
 *
 * @author Administrator
 * @create 2021/6/17
 * @since 1.0.0
 */
public interface RConstants {

    Integer SUCCESS = 200;

    Integer FAILURE = 400;

    /**
     * 默认为空消息
     */
    String DEFAULT_NULL_MESSAGE = "暂无承载数据";
    /**
     * 默认成功消息
     */
    String DEFAULT_SUCCESS_MESSAGE = "操作成功";
    /**
     * 默认失败消息
     */
    String DEFAULT_FAILURE_MESSAGE = "操作失败";

    String USER_NOT_EXIST = "-2000";

    /**
     * 其他异常
     */
    String ERROR = "-500";

    /**
     * 购物车为空
     */
    String CART_EMPTY = "-1000";

    /**
     * 库存不足
     */
    String NOT_ENOUGH = "-1001";

    /**
     * 保存订单失败
     */
    String ORDER_SCENE_ERROR = "-1002";

    /**
     * 订单不存在
     */
    String ORDER_NOT_EXIST = "-1003";

    /**
     * 商品已下架
     */
    String ORDER_WARES_EXPIRE = "-1004";

    /**
     * 订单已申请退单
     */
    String ORDER_ALREADY_REFUND = "-1005";

    /**
     * 订单未退单
     */
    String ORDER_NOT_REFUND = "-1006";

    /**
     * 优惠券不存在
     */
    String COUPON_NOT_EXIST = "-1007";


}