/**
 * Copyright (C), 2015-2023, 263企业通信
 * FileName: OrderSumResult
 * Author:   Derek Xu
 * Date:     2023/4/19 13:13
 * Description:
 * History:
 * <author>          <time>          <version>          <desc>
 * 作者姓名           修改时间           版本号              描述
 */
package cn.com.xuct.group.purchase.vo.result;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import java.io.Serializable;

/**
 * 〈一句话功能简述〉<br>
 * 〈〉
 *
 * @author Derek Xu
 * @create 2023/4/19
 * @since 1.0.0
 */
@Data
public class OrderSumResult implements Serializable {

    @Schema(description = "待支付总数")
    private long toBePaidCount;

    @Schema(description = "待发货总数")
    private long toBeSendCount;

    @Schema(description = "待收货总数")
    private long toBeReceivedCount;

    @Schema(description = "待评价总数")
    private long toBeEvaluationCount;

    @Schema(description = "售后总数")
    private long toBeService;
}