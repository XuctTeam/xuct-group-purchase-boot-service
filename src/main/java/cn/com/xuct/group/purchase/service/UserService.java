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

/**
 * 〈一句话功能简述〉<br> 
 * 〈〉
 *
 * @author Derek Xu
 * @create 2023/5/12
 * @since 1.0.0
 */
public interface UserService extends IBaseService<UserMapper , User> {


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

}