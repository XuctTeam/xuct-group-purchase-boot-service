/**
 * Copyright (C), 2015-2023, 263企业通信
 * FileName: Member
 * Author:   Derek Xu
 * Date:     2023/5/12 10:18
 * Description:
 * History:
 * <author>          <time>          <version>          <desc>
 * 作者姓名           修改时间           版本号              描述
 */
package cn.com.xuct.group.purchase.entity;

import cn.com.xuct.group.purchase.base.dao.SuperEntity;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * 〈一句话功能简述〉<br> 
 * 〈〉
 *
 * @author Derek Xu
 * @create 2023/5/12
 * @since 1.0.0
 */
@Data
@EqualsAndHashCode(callSuper = true)
@TableName("sys_member")
public class Member extends SuperEntity<Member> {

    /**
     * OpenId
     */
    private String openId;

    /**
     * 头像
     */
    private String avatar;


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

    /**
     * 昵称
     */
    @TableField("nick_name")
    private String nickname;


    @TableField(exist = false)
    private String roleCode;


    public void cleanData() {
        super.setCreateTime(null);
        super.setUpdateTime(null);
        this.status = null;
    }
}