/**
 * Copyright (C), 2015-2023, 楚恬商场
 * FileName: UpdateCartNumParam
 * Author:   Derek Xu
 * Date:     2023/4/2 22:03
 * Description:
 * History:
 * <author>          <time>          <version>          <desc>
 * Derek Xu           修改时间           版本号              描述
 */
package cn.com.xuct.group.purchase.vo.param;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import javax.validation.constraints.NotNull;

/**
 * 〈一句话功能简述〉<br> 
 * 〈〉
 *
 * @author Derek Xu
 * @create 2023/4/2
 * @since 1.0.0
 */
@Data
public class UpdateCartNumParam extends WaresParam {

    @NotNull
    @Schema(description = "数量")
    private Integer num;
}