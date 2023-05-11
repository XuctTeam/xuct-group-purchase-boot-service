/**
 * Copyright (C), 2015-2023, 263企业通信
 * FileName: RoleResource
 * Author:   Derek Xu
 * Date:     2023/5/9 9:45
 * Description:
 * History:
 * <author>          <time>          <version>          <desc>
 * 作者姓名           修改时间           版本号              描述
 */
package cn.com.xuct.group.purchase.entity;

import cn.com.xuct.group.purchase.base.dao.SuperEntity;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * 〈一句话功能简述〉<br> 
 * 〈〉
 *
 * @author Derek Xu
 * @create 2023/5/9
 * @since 1.0.0
 */
@EqualsAndHashCode(callSuper = true)
@Data
@TableName("sys_role_resource")
public class RoleResource extends SuperEntity<RoleResource> {


    private Long roleId;


    private Long resourceId;
}