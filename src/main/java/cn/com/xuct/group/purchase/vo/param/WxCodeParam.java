/**
 * Copyright (C), 2015-2023, XXX有限公司
 * FileName: WxCodeParam
 * Author:   Derek Xu
 * Date:     2023/3/17 18:07
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
 * @create 2023/3/17
 * @since 1.0.0
 */
@Data
public class WxCodeParam implements Serializable {

    @NotNull
    @Schema(name = "小程序Code")
    private String code;

}