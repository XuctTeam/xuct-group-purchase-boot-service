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
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.databind.ser.std.ToStringSerializer;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.EqualsAndHashCode;

import javax.validation.constraints.NotNull;

/**
 * 〈一句话功能简述〉<br>
 * 〈〉
 *
 * @author Derek Xu
 * @create 2023/3/16
 * @since 1.0.0
 */
@Data
@EqualsAndHashCode(callSuper = true)
@TableName("sys_user")
public class User extends SuperEntity<User> {

    @Schema(description = "用户名")
    @TableField("user_name")
    @NotNull
    private String username;


    @Schema(description = "密码")
    private String password;

    /**
     * OpenId
     */
    private String openId;

    /**
     * 头像
     */
    @Schema(description = "头像")
    private String avatar;

    /**
     * 角色ID
     */
    @JsonSerialize(using = ToStringSerializer.class)
    private Long roleId;

    /**
     * 状态 0正常 1冻结 2删除
     */
    private Integer status;

    /**
     * 电话
     */
    @Schema(description = "电话")
    private String phone;

    /**
     * 昵称
     */
    @TableField("nick_name")
    @Schema(description = "昵称")
    @NotNull
    private String nickname;


    @TableField(exist = false)
    private String roleCode;

    @Schema(description = "角色名称")
    @TableField(exist = false)
    private String roleName;


    public void cleanData() {
        super.setCreateTime(null);
        super.setUpdateTime(null);
        this.password = null;
        this.status = null;
    }

}