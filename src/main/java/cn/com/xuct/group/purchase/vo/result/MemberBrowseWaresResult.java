/**
 * Copyright (C), 2015-2023, 263企业通信
 * FileName: MemberBrowseWaresResult
 * Author:   Derek Xu
 * Date:     2023/6/2 17:19
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
import java.util.Date;

/**
 * 〈一句话功能简述〉<br> 
 * 〈〉
 *
 * @author Derek Xu
 * @create 2023/6/2
 * @since 1.0.0
 */
@Data
public class MemberBrowseWaresResult implements Serializable {


    @JsonSerialize(using = ToStringSerializer.class)
    @Schema(description = "ID")
    private Long id;

    @JsonSerialize(using = ToStringSerializer.class)
    @Schema(description = "商品ID")
    private Long waresId;

    @Schema(description = "商品名称")
    private String waresName;

    @Schema(description = "商品首页图片")
    private String waresFirstDrawing;

    @Schema(description = "商品单位")
    private String waresUnit;

    @Schema(description = "商品开始售卖时间")
    private Date waresStartTime;

    @Schema(description = "商品结束售卖时间")
    private Date waresEndTime;

    @Schema(description = "商品库存")
    private Integer waresInventory;

    @Schema(description = "浏览时间")
    private Date createTime;

}