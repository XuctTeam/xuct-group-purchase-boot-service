/**
 * Copyright (C), 2015-2023, 楚恬商场
 * FileName: RefundOrderParam
 * Author:   Derek Xu
 * Date:     2023/4/18 21:17
 * Description:
 * History:
 * <author>          <time>          <version>          <desc>
 * Derek Xu           修改时间           版本号              描述
 */
package cn.com.xuct.group.purchase.vo.param;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.util.List;

/**
 * 〈一句话功能简述〉<br>
 * 〈〉
 *
 * @author Derek Xu
 * @create 2023/4/18
 * @since 1.0.0
 */
@Data
public class RefundOrderParam implements Serializable {

    @Schema(description = "订单ID")
    @NotNull
    private String orderId;

    @Schema(description = "退单类型")
    @NotNull
    private String refundType;

    @Schema(description = "退单原因")
    private String refundReason;

    @Schema(description = "退单上传图片")
    private List<String> refundImages;
}