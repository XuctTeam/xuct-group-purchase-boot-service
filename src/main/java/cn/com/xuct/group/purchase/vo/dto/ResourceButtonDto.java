/**
 * Copyright (C), 2015-2023, 263企业通信
 * FileName: ResourceButtonDto
 * Author:   Derek Xu
 * Date:     2023/5/10 18:49
 * Description:
 * History:
 * <author>          <time>          <version>          <desc>
 * 作者姓名           修改时间           版本号              描述
 */
package cn.com.xuct.group.purchase.vo.dto;

import lombok.Data;

import java.io.Serializable;

/**
 * 〈一句话功能简述〉<br>
 * 〈〉
 *
 * @author Derek Xu
 * @create 2023/5/10
 * @since 1.0.0
 */
@Data
public class ResourceButtonDto implements Serializable {

    private String pathName;

    private String perm;
}