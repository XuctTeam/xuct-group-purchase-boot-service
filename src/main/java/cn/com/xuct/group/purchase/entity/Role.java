/**
 * Copyright (C), 2015-2023, XXX有限公司
 * FileName: Role
 * Author:   Derek Xu
 * Date:     2023/3/16 14:14
 * Description:
 * History:
 * <author>          <time>          <version>          <desc>
 * Derek Xu         修改时间           版本号              描述
 */
package cn.com.xuct.group.purchase.entity;

import cn.com.xuct.group.purchase.base.dao.SuperEntity;
import cn.com.xuct.group.purchase.constants.RoleCodeEnum;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

/**
 * 〈一句话功能简述〉<br> 
 * 〈〉
 *
 * @author Derek Xu
 * @create 2023/3/16
 * @since 1.0.0
 */
@Data
@TableName("sys_role")
public class Role extends SuperEntity<Role> {

    /**
     * 名称
     */
    private String name;

    /**
     * 编码
     */
    private RoleCodeEnum code;
}