/**
 * Copyright (C), 2015-2023, 263企业通信
 * FileName: BindPhoneParam
 * Author:   Derek Xu
 * Date:     2023/4/19 18:05
 * Description:
 * History:
 * <author>          <time>          <version>          <desc>
 * 作者姓名           修改时间           版本号              描述
 */
package cn.com.xuct.group.purchase.vo.param;

import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotNull;
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
public class BindPhoneParam implements Serializable {

    @Schema(description = "code")
    @NotNull
    private String code;

    @Schema(description = "encryptedData")
    @NotNull
    private String encryptedData;

    @Schema(description = "iv")
    @NotNull
    private String iv;
}