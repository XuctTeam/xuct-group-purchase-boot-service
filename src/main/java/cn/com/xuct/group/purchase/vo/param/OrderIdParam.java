/**
 * Copyright (C), 2015-2023, 楚恬商场
 * FileName: RushOrderParam
 * Author:   Derek Xu
 * Date:     2023/4/17 21:01
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

/**
 * 〈一句话功能简述〉<br>
 * 〈〉
 *
 * @author Derek Xu
 * @create 2023/4/17
 * @since 1.0.0
 */
@Data
public class OrderIdParam implements Serializable {

    @NotNull
    @Schema(description = "订单ID")
    private String orderId;

    @Schema(description = "原因")
    private String reason;
}