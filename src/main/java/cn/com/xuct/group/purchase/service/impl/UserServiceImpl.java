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
import cn.com.xuct.group.purchase.constants.RedisCacheConstants;
import cn.com.xuct.group.purchase.constants.RoleCodeEnum;
import cn.com.xuct.group.purchase.entity.Role;
import cn.com.xuct.group.purchase.entity.User;
import cn.com.xuct.group.purchase.mapper.UserMapper;
import cn.com.xuct.group.purchase.service.UserService;
import cn.hutool.core.date.DateUtil;
import com.github.yulichang.wrapper.MPJLambdaWrapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;
import org.springframework.util.StringUtils;

import java.util.List;

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

    @Override
    @Cacheable(cacheNames = RedisCacheConstants.USER_CACHE_USER_INFO, key = "#id", unless = "#result == null")
    public User findById(Long id) {
        return this.getById(id);
    }

    @Override
    public List<User> list(final String username, final Integer status, final String phone, final List<String> createTime) {
        MPJLambdaWrapper<User> qr = new MPJLambdaWrapper<User>()
                .selectAll(User.class)//查询user表全部字段
                .selectAs(Role::getName, User::getRoleName)
                .leftJoin(Role.class, Role::getId, User::getRoleId)
                .ne(Role::getCode, RoleCodeEnum.super_admin.name());
        if (StringUtils.hasLength(username)) {
            qr.like(User::getUsername, username);
        }
        if (status != null) {
            qr.eq(User::getStatus, status);
        }
        if (StringUtils.hasLength(phone)) {
            qr.eq(User::getPhone, phone);
        }
        if (!CollectionUtils.isEmpty(createTime)) {
            qr.between(User::getCreateTime, DateUtil.parseDate(createTime.get(0)), DateUtil.parseDate(createTime.get(1)));
        }
        return this.list(qr);
    }

    @Override
    public boolean changeStatus(Long userId, Integer status) {
        User existUser = this.getById(userId);
        if (existUser == null) {
            return false;
        }
        existUser.setStatus(status);
        this.updateById(existUser);
        return true;
    }

    @Override
    public void resetPassword(Long userId) {

    }
}