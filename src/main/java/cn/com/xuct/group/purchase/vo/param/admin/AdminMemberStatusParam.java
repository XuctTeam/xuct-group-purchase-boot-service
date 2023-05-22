/**
 * Copyright (C), 2015-2023, 263企业通信
 * FileName: AdminMemberStatusParam
 * Author:   Derek Xu
 * Date:     2023/5/22 16:59
 * Description:
 * History:
 * <author>          <time>          <version>          <desc>
 * 作者姓名           修改时间           版本号              描述
 */
package cn.com.xuct.group.purchase.vo.param.admin;

import lombok.Data;

import javax.validation.constraints.NotNull;
import java.io.Serializable;

/**
 * 〈一句话功能简述〉<br> 
 * 〈〉
 *
 * @author Derek Xu
 * @create 2023/5/22
 * @since 1.0.0
 */
@Data
public class AdminMemberStatusParam implements Serializable {

    @NotNull
    private Long id;

    @NotNull
    private Integer status;
}