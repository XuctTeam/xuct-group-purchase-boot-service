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

import cn.com.xuct.group.purchase.entity.Role;
import cn.com.xuct.group.purchase.entity.User;
import cn.com.xuct.group.purchase.service.RoleService;
import cn.com.xuct.group.purchase.service.UserService;
import cn.dev33.satoken.stp.StpInterface;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.assertj.core.util.Lists;
import org.springframework.stereotype.Service;

import java.util.List;

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

    @Override
    public List<String> getPermissionList(Object o, String s) {
        return Lists.newArrayList();
    }

    @Override
    public List<String> getRoleList(Object loginId, String loginType) {
        User user = userService.findById(Long.valueOf(String.valueOf(loginId)));
        if (user == null) {
            return Lists.newArrayList();
        }
        Role role = roleService.findById(user.getRoleId());
        if (role == null) {
            return Lists.newArrayList();
        }
        return Lists.newArrayList(role.getCode().name());
    }
}