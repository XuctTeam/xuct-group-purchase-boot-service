/**
 * Copyright (C), 2015-2023, 263企业通信
 * FileName: CartManyWaresParam
 * Author:   Derek Xu
 * Date:     2023/4/3 9:17
 * Description:
 * History:
 * <author>          <time>          <version>          <desc>
 * 作者姓名           修改时间           版本号              描述
 */
package cn.com.xuct.group.purchase.vo.param;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.util.List;

/**
 * 〈一句话功能简述〉<br>
 * 〈〉
 *
 * @author Derek Xu
 * @create 2023/4/3
 * @since 1.0.0
 */
@Data
public class CartManyWaresParam implements Serializable {

    @Schema(description = "场景值")
    @NotNull
    private String scene;

    @Schema(description = "购物车商品IDS")
    @NotEmpty
    private List<Long> waresIds;
}