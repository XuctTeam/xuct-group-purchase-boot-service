/**
 * Copyright (C), 2015-2023, 263企业通信
 * FileName: UserOrderItem
 * Author:   Derek Xu
 * Date:     2023/4/7 10:42
 * Description:
 * History:
 * <author>          <time>          <version>          <desc>
 * 作者姓名           修改时间           版本号              描述
 */
package cn.com.xuct.group.purchase.entity;

import cn.com.xuct.group.purchase.base.dao.SuperEntity;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.databind.ser.std.ToStringSerializer;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * 〈一句话功能简述〉<br>
 * 〈〉
 *
 * @author Derek Xu
 * @create 2023/4/7
 * @since 1.0.0
 */
@EqualsAndHashCode(callSuper = true)
@Data
@TableName("bu_member_order_item")
public class MemberOrderItem extends SuperEntity<MemberOrderItem> {

    @Schema(description = "订单ID")
    @JsonSerialize(using = ToStringSerializer.class)
    private Long orderId;

    @Schema(description = "用户ID")
    private Long memberId;

    @Schema(description = "商品ID")
    @JsonSerialize(using = ToStringSerializer.class)
    private Long goodId;

    @Schema(description = "购买数量")
    private Integer num;

    @Schema(description = "金额")
    private Long price;

    @Schema(description = "是否评价")
    private boolean evaluation = false;

    @Schema(description = "商品名称")
    @TableField(exist = false)
    private String goodName;

    @Schema(description = "商品首页图")
    @TableField(exist = false)
    private String goodFirstDrawing;

    @Schema(description = "商品单位")
    @TableField(exist = false)
    private String goodUnit;



}