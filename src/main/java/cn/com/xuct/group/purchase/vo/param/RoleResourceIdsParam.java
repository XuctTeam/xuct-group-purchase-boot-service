/**
 * Copyright (C), 2015-2023, 263企业通信
 * FileName: RoleResourceIdsParam
 * Author:   Derek Xu
 * Date:     2023/5/11 16:46
 * Description:
 * History:
 * <author>          <time>          <version>          <desc>
 * 作者姓名           修改时间           版本号              描述
 */
package cn.com.xuct.group.purchase.vo.param;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

import java.io.Serializable;
import java.util.List;

/**
 * 〈一句话功能简述〉<br>
 * 〈〉
 *
 * @author Derek Xu
 * @create 2023/5/11
 * @since 1.0.0
 */
@Data
public class RoleResourceIdsParam implements Serializable {

    @Schema(description = "资源IDS")
    private List<Long> resourceIds;

    @NotNull
    @Schema(description = "角色ID")
    private Long roleId;
}