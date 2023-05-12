/**
 * Copyright (C), 2015-2023, 263企业通信
 * FileName: AdminMenuTreeData
 * Author:   Derek Xu
 * Date:     2023/5/10 11:24
 * Description:
 * History:
 * <author>          <time>          <version>          <desc>
 * 作者姓名           修改时间           版本号              描述
 */
package cn.com.xuct.group.purchase.vo.result.admin;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.databind.ser.std.ToStringSerializer;
import lombok.Data;

import java.io.Serializable;
import java.util.List;

/**
 * 〈一句话功能简述〉<br>
 * 〈〉
 *
 * @author Derek Xu
 * @create 2023/5/10
 * @since 1.0.0
 */
@Data
public class AdminMenuTreeResult implements Serializable {

    @JsonSerialize(using = ToStringSerializer.class)
    private Long id;

    private String value;

    private String label;

    private Integer sort;

    private List<AdminMenuTreeResult> children;

}