/**
 * Copyright (C), 2015-2023, 263企业通信
 * FileName: CartResult
 * Author:   Derek Xu
 * Date:     2023/3/31 13:32
 * Description:
 * History:
 * <author>          <time>          <version>          <desc>
 * 作者姓名           修改时间           版本号              描述
 */
package cn.com.xuct.group.purchase.vo.result;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.databind.ser.std.ToStringSerializer;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import java.io.Serializable;

/**
 * 〈一句话功能简述〉<br>
 * 〈〉
 *
 * @author Derek Xu
 * @create 2023/3/31
 * @since 1.0.0
 */
@Data
public class CartResult implements Serializable {

    @JsonSerialize(using = ToStringSerializer.class)
    @Schema(description = "商品ID")
    private Long waresId;

    @Schema(description = "名称")
    private String name;

    @Schema(description = "首页图片")
    private String firstDrawing;

    @Schema(description = "购买数量")
    private Integer num;

    @Schema(description = "单位")
    private String unit;

    @Schema(description = "库存")
    private Integer inventory;

    @Schema(description = "状态")
    private Integer status;

    @Schema(description = "是否删除")
    private boolean deleted;
}