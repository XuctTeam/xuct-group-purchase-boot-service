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
import cn.com.xuct.group.purchase.constants.RoleCodeEnum;
import cn.com.xuct.group.purchase.entity.Role;
import cn.com.xuct.group.purchase.entity.User;
import cn.com.xuct.group.purchase.mapper.UserMapper;
import cn.com.xuct.group.purchase.service.RoleService;
import cn.com.xuct.group.purchase.service.UserService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

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
        User user =  super.get(Column.of("open_id" , openId));
        if(user == null){
            Role role = roleService.get(Column.of("code" , RoleCodeEnum.member.name()));
            user = new User();
            user.setOpenId(openId);
            user.setRoleId(role.getId());
            user.setRoleCode(role.getCode());
            super.save(user);
        }
        return user;
    }
}