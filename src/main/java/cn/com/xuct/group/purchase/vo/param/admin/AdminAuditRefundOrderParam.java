/**
 * Copyright (C), 2015-2023, 263企业通信
 * FileName: AdminAuditRefundOrderParam
 * Author:   Derek Xu
 * Date:     2023/5/25 15:05
 * Description:
 * History:
 * <author>          <time>          <version>          <desc>
 * 作者姓名           修改时间           版本号              描述
 */
package cn.com.xuct.group.purchase.vo.param.admin;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import javax.validation.constraints.NotNull;

/**
 * 〈一句话功能简述〉<br>
 * 〈〉
 *
 * @author Derek Xu
 * @create 2023/5/25
 * @since 1.0.0
 */
@Data
public class AdminAuditRefundOrderParam {

    @Schema(description = "订单ID")
    @NotNull
    private Long id;

    @Schema(description = "反馈状态 0 - 不同意 1- 同意")
    @NotNull
    private Integer feedback;

    @Schema(description = "反馈内容")
    @NotNull
    private String content;
}