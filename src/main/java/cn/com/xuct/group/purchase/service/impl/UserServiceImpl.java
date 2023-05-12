/**
 * Copyright (C), 2015-2023, 263企业通信
 * FileName: UserServiceImpl
 * Author:   Derek Xu
 * Date:     2023/5/12 11:03
 * Description:
 * History:
 * <author>          <time>          <version>          <desc>
 * 作者姓名           修改时间           版本号              描述
 */
package cn.com.xuct.group.purchase.service.impl;

import cn.com.xuct.group.purchase.base.service.BaseServiceImpl;
import cn.com.xuct.group.purchase.base.vo.Column;
import cn.com.xuct.group.purchase.entity.User;
import cn.com.xuct.group.purchase.mapper.UserMapper;
import cn.com.xuct.group.purchase.service.UserService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

/**
 * 〈一句话功能简述〉<br>
 * 〈〉
 *
 * @author Derek Xu
 * @create 2023/5/12
 * @since 1.0.0
 */
@Slf4j
@Service
public class UserServiceImpl extends BaseServiceImpl<UserMapper, User> implements UserService {

    @Override
    public User updatePassword(Long memberId, String pass) {
        User user = this.getById(memberId);
        if (user == null) {
            return null;
        }
        user.setPassword(pass);
        this.updateById(user);
        return user;
    }

    @Override
    public User findByUsername(String username) {
        return this.get(Column.of("user_name", username));
    }
}