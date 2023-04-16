/**
 * Copyright (C), 2015-2023, 263企业通信
 * FileName: OrderParam
 * Author:   Derek Xu
 * Date:     2023/4/10 9:39
 * Description:
 * History:
 * <author>          <time>          <version>          <desc>
 * 作者姓名           修改时间           版本号              描述
 */
package cn.com.xuct.group.purchase.vo.param;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

import java.io.Serializable;
import java.util.List;

/**
 * 〈一句话功能简述〉<br>
 * 〈〉
 *
 * @author Derek Xu
 * @create 2023/4/10
 * @since 1.0.0
 */
@Data
public class OrderParam implements Serializable {

    @Schema(description = "场景 cart 购物车 good 立即购买")
    private String scene;

    @NotNull
    @Schema(description = "收货地址")
    private Long addressId;

    @NotNull
    @Schema(description = "使用积分")
    private Integer integral;


    @Schema(description = "备注")
    private String remarks;

    @NotEmpty
    @Schema(description = "商品IDS")
    private List<Long> goodIds;

}