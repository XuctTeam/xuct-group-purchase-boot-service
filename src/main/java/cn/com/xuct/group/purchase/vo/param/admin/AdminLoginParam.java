/**
 * Copyright (C), 2015-2023, 263企业通信
 * FileName: AdminLoginParam
 * Author:   Derek Xu
 * Date:     2023/5/8 16:20
 * Description:
 * History:
 * <author>          <time>          <version>          <desc>
 * 作者姓名           修改时间           版本号              描述
 */
package cn.com.xuct.group.purchase.vo.param.admin;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import javax.validation.constraints.NotNull;
import java.io.Serializable;

/**
 * 〈一句话功能简述〉<br> 
 * 〈〉
 *
 * @author Derek Xu
 * @create 2023/5/8
 * @since 1.0.0
 */
@Data
public class AdminLoginParam implements Serializable {

    @Schema(description = "用户名")
    @NotNull
    private String username;

    @Schema(description = "密码")
    @NotNull
    private String password;

}