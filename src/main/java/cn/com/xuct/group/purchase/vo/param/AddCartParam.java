/**
 * Copyright (C), 2015-2023, XXX有限公司
 * FileName: AddCartParam
 * Author:   Derek Xu
 * Date:     2023/3/29 10:43
 * Description:
 * History:
 * <author>          <time>          <version>          <desc>
 * Derek Xu         修改时间           版本号              描述
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
 * @create 2023/3/29
 * @since 1.0.0
 */
@Data
public class AddCartParam implements Serializable {


    @Schema(description = "商品ID")
    @NotNull
    private Long waresId;
}