/**
 * Copyright (C), 2015-2023, 263企业通信
 * FileName: MemberWaresEvaluate
 * Author:   Derek Xu
 * Date:     2023/4/23 17:53
 * Description: 用户商品评价
 * History:
 * <author>          <time>          <version>          <desc>
 * 作者姓名           修改时间           版本号              描述
 */
package cn.com.xuct.group.purchase.entity;

import cn.com.xuct.group.purchase.base.dao.SuperEntity;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * 〈一句话功能简述〉<br>
 * 〈用户商品评价〉
 *
 * @author Derek Xu
 * @create 2023/4/23
 * @since 1.0.0
 */
@EqualsAndHashCode(callSuper = true)
@Data
@TableName("bu_member_wares_evaluate")
public class MemberWaresEvaluate extends SuperEntity<MemberWaresEvaluate> {

    @Schema(description = "评价人")
    private Long memberId;

    @Schema(description = "评价商品")
    private Long waresId;

    @Schema(description = "评价的订单明细")
    private Long orderItemId;

    @Schema(description = "打分")
    private String rate;

    @Schema(description = "评价图片")
    private String evaluateImages;

    @Schema(description = "描述")
    private String remarks;

    @Schema(description = "评价商品名称")
    @TableField(exist = false)
    private String waresName;

    @Schema(description = "评价商品首页图片")
    @TableField(exist = false)
    private String waresFirstDrawing;

    @Schema(description = "评价人名称")
    @TableField(exist = false)
    private String memberName;

    @Schema(description = "评价人头像")
    @TableField(exist = false)
    private String memberAvatar;
}