/**
 * Copyright (C), 2015-2023, XXX有限公司
 * FileName: UserParam
 * Author:   Derek Xu
 * Date:     2023/3/22 16:45
 * Description:
 * History:
 * <author>          <time>          <version>          <desc>
 * Derek Xu         修改时间           版本号              描述
 */
package cn.com.xuct.group.purchase.vo.param;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

import java.io.Serializable;

/**
 * 〈一句话功能简述〉<br> 
 * 〈〉
 *
 * @author Derek Xu
 * @create 2023/3/22
 * @since 1.0.0
 */
@Data
public class UserParam implements Serializable {

    @Schema(title = "电话")
    @NotNull(message = "电话不能为空")
    private String phone;

    @Schema(title = "昵称")
    @NotNull(message = "昵称不能为空")
    private String nickname;
}