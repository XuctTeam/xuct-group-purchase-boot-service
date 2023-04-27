/**
 * Copyright (C), 2015-2023, 楚恬商场
 * FileName: OrderResult
 * Author:   Derek Xu
 * Date:     2023/4/16 22:17
 * Description:
 * History:
 * <author>          <time>          <version>          <desc>
 * Derek Xu           修改时间           版本号              描述
 */
package cn.com.xuct.group.purchase.vo.result;

import cn.com.xuct.group.purchase.entity.UserAddress;
import cn.com.xuct.group.purchase.entity.UserCoupon;
import cn.com.xuct.group.purchase.entity.UserOrder;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.io.Serializable;

/**
 * 〈一句话功能简述〉<br>
 * 〈〉
 *
 * @author Derek Xu
 * @create 2023/4/16
 * @since 1.0.0
 */
@EqualsAndHashCode(callSuper = true)
@Data
public class OrderResult extends UserOrder implements Serializable {

    @Schema(description = "收货地址")
    private UserAddress address;

    @Schema(description = "优惠券")
    private UserCoupon coupon;
}