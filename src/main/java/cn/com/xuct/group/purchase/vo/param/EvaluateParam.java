/**
 * Copyright (C), 2015-2023, 263企业通信
 * FileName: EvaluateParam
 * Author:   Derek Xu
 * Date:     2023/4/23 17:42
 * Description:
 * History:
 * <author>          <time>          <version>          <desc>
 * 作者姓名           修改时间           版本号              描述
 */
package cn.com.xuct.group.purchase.vo.param;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import java.io.Serializable;
import java.util.List;

/**
 * 〈一句话功能简述〉<br>
 * 〈〉
 *
 * @author Derek Xu
 * @create 2023/4/23
 * @since 1.0.0
 */
@Data
public class EvaluateParam implements Serializable {

    @Schema(description = "订单明细ID")
    private String orderItemId;

    @Schema(description = "打分")
    private String rate;

    @Schema(description = "上传图片")
    private List<String> evaluateImages;

    @Schema(description = "描述")
    private String remarks;
}