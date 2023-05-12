/**
 * Copyright (C), 2015-2023, 263企业通信
 * FileName: UserService
 * Author:   Derek Xu
 * Date:     2023/5/12 11:02
 * Description:
 * History:
 * <author>          <time>          <version>          <desc>
 * 作者姓名           修改时间           版本号              描述
 */
package cn.com.xuct.group.purchase.service;

import cn.com.xuct.group.purchase.base.service.IBaseService;
import cn.com.xuct.group.purchase.entity.User;
import cn.com.xuct.group.purchase.mapper.UserMapper;

import java.util.List;

/**
 * 〈一句话功能简述〉<br>
 * 〈〉
 *
 * @author Derek Xu
 * @create 2023/5/12
 * @since 1.0.0
 */
public interface UserService extends IBaseService<UserMapper, User> {

    /**
     * 修改密码
     *
     * @param userId
     * @param pass
     * @return
     */
    User updatePassword(final Long userId, final String pass);


    /**
     * 通过账号查询
     *
     * @param username
     * @return
     */
    User findByUsername(final String username);

    /**
     * 获取用户
     *
     * @param id
     * @return
     */
    User findById(final Long id);


    /**
     * 用户列表
     *
     * @param username
     * @param status
     * @param phone
     * @param createTime
     * @return
     */
    List<User> list(final String username, final Integer status, final String phone, final List<String> createTime);

    /**
     * 修改用户状态
     *
     * @param userId
     * @param status
     */
    boolean changeStatus(final Long userId, final Integer status);

    /**
     * 重置密码
     *
     * @param userId
     */
    void resetPassword(final Long userId);

}