/**
 * Copyright (C), 2015-2023, 楚恬商场
 * FileName: UserServiceImpl
 * Author:   Derek Xu
 * Date:     2023/3/18 14:19
 * Description:
 * History:
 * <author>          <time>          <version>          <desc>
 * Derek Xu           修改时间           版本号              描述
 */
package cn.com.xuct.group.purchase.service.impl;

import cn.com.xuct.group.purchase.base.service.BaseServiceImpl;
import cn.com.xuct.group.purchase.base.vo.Column;
import cn.com.xuct.group.purchase.constants.RedisCacheConstants;
import cn.com.xuct.group.purchase.constants.RoleCodeEnum;
import cn.com.xuct.group.purchase.entity.Role;
import cn.com.xuct.group.purchase.entity.User;
import cn.com.xuct.group.purchase.mapper.UserMapper;
import cn.com.xuct.group.purchase.service.RoleService;
import cn.com.xuct.group.purchase.service.UserService;
import cn.com.xuct.group.purchase.vo.result.UserSumResult;
import com.github.yulichang.wrapper.MPJLambdaWrapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

/**
 * 〈一句话功能简述〉<br>
 * 〈〉
 *
 * @author Derek Xu
 * @create 2023/3/18
 * @since 1.0.0
 */
@Slf4j
@Service
@RequiredArgsConstructor
public class UserServiceImpl extends BaseServiceImpl<UserMapper, User> implements UserService {
    private final RoleService roleService;

    @Override
    public User findByOpenId(String openId) {
        User user = super.get(Column.of("open_id", openId));
        if (user == null) {
            Role role = roleService.get(Column.of("code", RoleCodeEnum.member.name()));
            user = new User();
            user.setOpenId(openId);
            user.setRoleId(role.getId());
            user.setRoleCode(role.getCode());
            super.save(user);
        }
        return user;
    }

    @Override
    @Cacheable(cacheNames = RedisCacheConstants.USER_CACHE_ABLE_CACHE_NAME, key = "#id", unless = "#result == null")
    public User findById(Long id) {

        MPJLambdaWrapper<User> wrapper = new MPJLambdaWrapper<User>()
                .selectAll(User.class)
                .select(Role::getCode)
                .selectAs(Role::getCode, User::getRoleCode)//别名 t.address AS userAddress
                .leftJoin(Role.class, Role::getId, User::getRoleId)
                .eq(User::getId, id);
        return super.getBaseMapper().selectOne(wrapper);
    }

    @Override
    @CachePut(cacheNames = RedisCacheConstants.USER_CACHE_ABLE_CACHE_NAME, key = "#user.id", unless = "#result == null")
    public User updateUserInfo(User user, String phone, String nickname, String avatar) {
        if (StringUtils.hasLength(phone)) {
            user.setPhone(phone);
        }
        if (StringUtils.hasLength(nickname)) {
            user.setNickname(nickname);
        }
        if (StringUtils.hasLength(avatar)) {
            user.setAvatar(avatar);
        }
        this.updateById(user);
        return user;
    }

    @Override
    @CachePut(cacheNames = RedisCacheConstants.USER_CACHE_ABLE_CACHE_NAME, key = "#userId", unless = "#result == null")
    public User updateUserIntegral(Long userId, Integer integral) {
        User user = this.getById(userId);
        if (user == null) {
            return null;
        }
        user.setIntegral(user.getIntegral() + integral);
        this.updateById(user);
        return user;
    }

    @Override
    public UserSumResult userSum(final Long userId) {
        return ((UserMapper) this.getBaseMapper()).userSum(userId);
    }
}