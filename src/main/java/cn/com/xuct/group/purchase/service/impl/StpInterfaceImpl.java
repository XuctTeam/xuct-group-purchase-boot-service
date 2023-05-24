/**
 * Copyright (C), 2015-2023, 楚恬商场
 * FileName: StpInterfaceImpl
 * Author:   Derek Xu
 * Date:     2023/3/18 23:21
 * Description:
 * History:
 * <author>          <time>          <version>          <desc>
 * Derek Xu           修改时间           版本号              描述
 */
package cn.com.xuct.group.purchase.service.impl;

import cn.com.xuct.group.purchase.entity.Resource;
import cn.com.xuct.group.purchase.entity.Role;
import cn.com.xuct.group.purchase.entity.User;
import cn.com.xuct.group.purchase.service.ResourceService;
import cn.com.xuct.group.purchase.service.RoleService;
import cn.com.xuct.group.purchase.service.UserService;
import cn.dev33.satoken.stp.StpInterface;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.assertj.core.util.Lists;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

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
public class StpInterfaceImpl implements StpInterface {

    private final RoleService roleService;

    private final UserService userService;

    private final ResourceService resourceService;

    @Override
    public List<String> getPermissionList(Object loginId, String loginType) {
        User user = this.getUserById(loginId);
        if (user == null) {
            return Lists.newArrayList();
        }
        List<Resource> resources = resourceService.findUserPermissionList(user.getRoleId());
        return resources.stream().map(Resource::getPerm).collect(Collectors.toList());
    }

    @Override
    public List<String> getRoleList(Object loginId, String loginType) {
        User user = this.getUserById(loginId);
        if (user == null) {
            return Lists.newArrayList();
        }
        Role role = roleService.findById(user.getRoleId());
        if (role == null) {
            return Lists.newArrayList();
        }
        return Lists.newArrayList(role.getCode());
    }

    private User getUserById(final Object userId) {
        return userService.getById(Long.valueOf(String.valueOf(userId)));
    }
}