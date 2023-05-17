/**
 * Copyright (C), 2015-2023, 楚恬商场
 * FileName: Coupon
 * Author:   Derek Xu
 * Date:     2023/4/26 14:13
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

import java.util.List;

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
@TableName("bu_coupon")
public class Coupon extends SuperEntity<Coupon> {

    @Schema(description = "名称")
    private String name;

    @Schema(description = "面额")
    private Long price;

    @Schema(description = "满减金额")
    private Long fullPrice;

    @Schema(description = "有效期")
    private Integer effective;

    @Schema(description = "是否启用")
    private boolean used;

    @Schema(description = "商品类型 0全部商品 1部分商品")
    private int waresType;

    @Schema(description = "是否删除")
    private boolean deleted;


    @Schema(description = "可使用商品")
    @TableField(exist = false)
    private List<Long> waresIds;
}