/**
 * Copyright (C), 2015-2023, 楚恬商场
 * FileName: UserCoupon
 * Author:   Derek Xu
 * Date:     2023/4/26 14:09
 * Description:
 * History:
 * <author>          <time>          <version>          <desc>
 * Derek Xu           修改时间           版本号              描述
 */
package cn.com.xuct.group.purchase.entity;

import cn.com.xuct.group.purchase.base.dao.SuperEntity;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.util.Date;

/**
 * 〈一句话功能简述〉<br>
 * 〈〉
 *
 * @author Derek Xu
 * @create 2023/4/26
 * @since 1.0.0
 */
@EqualsAndHashCode(callSuper = true)
@Data
@TableName("bu_member_coupon")
public class MemberCoupon extends SuperEntity<MemberCoupon> {

    @Schema(description = "用户ID")
    private Long memberId;

    @Schema(description = "优惠券ID")
    private Long couponId;

    @Schema(description = "有效期开始时间")
    private Date beginTime;

    @Schema(description = "有效期结束时间")
    private Date endTime;

    @Schema(description = "是否已用")
    private boolean used;

    @TableField(exist = false)
    @Schema(description = "优惠券面额")
    private Long couponPrice;

    @TableField(exist = false)
    @Schema(description = "优惠券满减金额")
    private Long couponFullPrice;

    @TableField(exist = false)
    @Schema(description = "优惠券名字")
    private String couponName;
}