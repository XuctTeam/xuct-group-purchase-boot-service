/**
 * Copyright (C), 2015-2023, 263企业通信
 * FileName: UserOrder
 * Author:   Derek Xu
 * Date:     2023/4/7 10:27
 * Description:
 * History:
 * <author>          <time>          <version>          <desc>
 * 作者姓名           修改时间           版本号              描述
 */
package cn.com.xuct.group.purchase.entity;

import cn.com.xuct.group.purchase.base.dao.SuperEntity;
import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.databind.ser.std.ToStringSerializer;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.util.Date;
import java.util.List;

/**
 * 〈一句话功能简述〉<br>
 * 〈〉
 *
 * @author Derek Xu
 * @create 2023/4/7
 * @since 1.0.0
 */
@Data
@EqualsAndHashCode(callSuper = true)
@TableName("bu_user_order")
@JsonIgnoreProperties(value = {"handler"})
public class UserOrder extends SuperEntity<UserOrder> {

    @TableId(value = "id", type = IdType.ASSIGN_ID)
    @JsonSerialize(using = ToStringSerializer.class)
    private Long id;

    @Schema(description = "用户ID")
    private Long userId;

    @Schema(description = "收货地址ID")
    private Long addressId;

    @Schema(description = "使用积分")
    private Integer integral;

    @Schema(description = "备注")
    private String remarks;

    @Schema(description = "商品总数")
    private Integer goodNum;

    @Schema(description = "总金额")
    private Long totalPrice;

    @Schema(description = "状态 1待付款  2待配送 3待收货  4已完成")
    private Integer status;

    @Schema(description = "配送时间")
    private Date deliverTime;

    @Schema(description = "配送员")
    private String deliveryMan;

    @Schema(description = "送达时间")
    private Date serveTime;

    @Schema(description = "送达图片")
    private String serveImage;

    @Schema(description = "完成时间")
    private Date successTime;

    @TableField(exist = false)
    private List<UserOrderItem> items;

}