/**
 * Copyright (C), 2015-2023, XXX有限公司
 * FileName: User
 * Author:   Derek Xu
 * Date:     2023/3/16 13:23
 * Description:
 * History:
 * <author>          <time>          <version>          <desc>
 * Derek Xu         修改时间           版本号              描述
 */
package cn.com.xuct.group.purchase.entity;

import cn.com.xuct.group.purchase.base.dao.SuperEntity;
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
@TableName("sys_user")
public class User extends SuperEntity<User> {

    /**
     * OpenId
     */
    private String openId;

    /**
     * 头像
     */
    private String avatar;

    /**
     * 角色ID
     */
    private Long roleId;

    /**
     * 状态 0正常 1冻结 2删除
     */
    private Integer status;

    /**
     * 电话
     */
    private String phone;

    /**
     * 积分
     */
    private Long integral;

}