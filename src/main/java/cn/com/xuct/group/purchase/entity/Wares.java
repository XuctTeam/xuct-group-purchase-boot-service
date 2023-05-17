/**
 * Copyright (C), 2015-2023, XXX有限公司
 * FileName: Good
 * Author:   Derek Xu
 * Date:     2023/3/27 11:11
 * Description:
 * History:
 * <author>          <time>          <version>          <desc>
 * Derek Xu         修改时间           版本号              描述
 */
package cn.com.xuct.group.purchase.entity;

import cn.com.xuct.group.purchase.base.dao.SuperEntity;
import com.baomidou.mybatisplus.annotation.FieldFill;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import com.baomidou.mybatisplus.annotation.Version;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.databind.ser.std.ToStringSerializer;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import java.util.Date;

/**
 * 〈一句话功能简述〉<br>
 * 〈〉
 *
 * @author Derek Xu
 * @create 2023/3/27
 * @since 1.0.0
 */
@EqualsAndHashCode(callSuper = true)
@Data
@TableName("bu_wares")
@NoArgsConstructor
@AllArgsConstructor
public class Wares extends SuperEntity<Wares> {


    @Schema(description = "名称")
    private String name;

    @Schema(description = "首页图片")
    private String firstDrawing;

    @Schema(description = "简介")
    private String blurb;

    @JsonSerialize(using = ToStringSerializer.class)
    @Schema(description = "分类ID")
    private Long categoryId;

    @Schema(description = "标签(通过，分割)" )
    private String tags;

    @Schema(description = "详情轮播图(通过，分割)")
    private String swiperImages;

    @Schema(description = "状态(0未上架 1已上架)")
    private Integer status;

    @Schema(description = "开始售卖时间")
    private Date startTime;

    @Schema(description = "结束售卖时间")
    private Date endTime;

    @Schema(description = "库存")
    private Integer inventory;

    @Schema(description = "描述")
    private String detail;

    @Schema(description = "单位")
    private String unit;

    @Schema(description = "是否删除")
    private boolean deleted;

    @TableField(value = "version", fill = FieldFill.INSERT)
    @Version
    private Integer version;
}