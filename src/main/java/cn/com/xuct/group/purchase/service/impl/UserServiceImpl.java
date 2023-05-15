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
import cn.com.xuct.group.purchase.config.DefaultProperties;
import cn.com.xuct.group.purchase.constants.RedisCacheConstants;
import cn.com.xuct.group.purchase.constants.RoleCodeEnum;
import cn.com.xuct.group.purchase.entity.Role;
import cn.com.xuct.group.purchase.entity.User;
import cn.com.xuct.group.purchase.mapper.UserMapper;
import cn.com.xuct.group.purchase.service.UserService;
import cn.dev33.satoken.stp.StpUtil;
import cn.hutool.core.date.DateUtil;
import cn.hutool.crypto.SecureUtil;
import com.github.yulichang.wrapper.MPJLambdaWrapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.CachePut;
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
@RequiredArgsConstructor
public class UserServiceImpl extends BaseServiceImpl<UserMapper, User> implements UserService {


    private final DefaultProperties defaultProperties;

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


    @CachePut(cacheNames = RedisCacheConstants.USER_CACHE_USER_INFO, key = "#user.id", unless = "#result == null")
    @Override
    public User updateUser(User user) {
        this.updateById(user);
        return user;
    }

    @Override
    public void addUser(User user) {
        user.setStatus(0);
        user.setPassword(SecureUtil.md5(defaultProperties.getUserPassword()));
        this.save(user);
    }

    @Override
    @CacheEvict(cacheNames = RedisCacheConstants.USER_CACHE_USER_INFO, key = "#userId")
    public void deleteUser(Long userId) {
        User user = this.getById(userId);
        if (user == null) {
            return;
        }
        /* 禁止账号 */
        StpUtil.disable(userId, -1);
        this.removeById(userId);
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
        /* 冻结账号 */
        if (status == 1 && !StpUtil.isDisable(userId)) {
            StpUtil.disable(userId, -1);
        }
        //解封账号
        if (status == 0 && StpUtil.isDisable(userId)) {
            StpUtil.untieDisable(userId);
        }
        existUser.setStatus(status);
        this.updateById(existUser);
        return true;
    }

    @Override
    public void resetPassword(Long userId) {
        User existUser = this.getById(userId);
        if (existUser == null) {
            return;
        }
        existUser.setPassword(SecureUtil.md5(defaultProperties.getUserPassword()));
        this.updateById(existUser);
    }
}