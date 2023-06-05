/**
 * Copyright (C), 2015-2023, 263企业通信
 * FileName: CouponWares
 * Author:   Derek Xu
 * Date:     2023/5/17 16:55
 * Description:
 * History:
 * <author>          <time>          <version>          <desc>
 * 作者姓名           修改时间           版本号              描述
 */
package cn.com.xuct.group.purchase.entity;

import cn.com.xuct.group.purchase.base.dao.SuperEntity;
import com.baomidou.mybatisplus.annotation.TableName;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.databind.ser.std.ToStringSerializer;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * 〈一句话功能简述〉<br>
 * 〈优惠券关联的部分商品〉
 *
 * @author Derek Xu
 * @create 2023/5/17
 * @since 1.0.0
 */
@EqualsAndHashCode(callSuper = true)
@Data
@TableName("bu_coupon_wares")
public class CouponWares extends SuperEntity<CouponWares> {

    @JsonSerialize(using = ToStringSerializer.class)
    @Schema(description = "优惠券ID")
    private Long couponId;

    @JsonSerialize(using = ToStringSerializer.class)
    @Schema(description = "商品ID")
    private Long waresId;
}