/**
 * Copyright (C), 2015-2023, 263企业通信
 * FileName: AdminUserController
 * Author:   Derek Xu
 * Date:     2023/5/9 9:14
 * Description:
 * History:
 * <author>          <time>          <version>          <desc>
 * 作者姓名           修改时间           版本号              描述
 */
package cn.com.xuct.group.purchase.controller.admin;

import cn.com.xuct.group.purchase.annotation.Log;
import cn.com.xuct.group.purchase.base.res.R;
import cn.com.xuct.group.purchase.constants.OptConstants;
import cn.com.xuct.group.purchase.entity.User;
import cn.com.xuct.group.purchase.service.RoleService;
import cn.com.xuct.group.purchase.service.UserService;
import cn.com.xuct.group.purchase.vo.param.admin.AdminModifyPasswordParam;
import cn.com.xuct.group.purchase.vo.param.admin.AdminUserChangeStatusParam;
import cn.com.xuct.group.purchase.vo.param.admin.AdminUserParam;
import cn.com.xuct.group.purchase.vo.result.admin.AdminMenuResult;
import cn.dev33.satoken.annotation.SaCheckRole;
import cn.dev33.satoken.annotation.SaMode;
import cn.dev33.satoken.stp.StpUtil;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

/**
 * 〈一句话功能简述〉<br>
 * 〈〉
 *
 * @author Derek Xu
 * @create 2023/5/9
 * @since 1.0.0
 */
@Tag(name = "【管理员-用户模块】")
@RequestMapping("/api/admin/v1/user")
@RequiredArgsConstructor
@RestController
public class AdminUserController {

    private final RoleService roleService;

    private final UserService userService;

    @Operation(summary = "【用户】菜单列表", description = "菜单列表")
    @GetMapping("/menu")
    public R<List<AdminMenuResult>> getUserMenuList() {
        return R.data(roleService.getUserMenuList(StpUtil.getLoginIdAsLong()));
    }

    @Operation(summary = "【用户】按钮权限", description = "按钮权限")
    @GetMapping("/button")
    public R<Map<String, List<String>>> getUserButton() {
        return R.data(roleService.getUserButtonList(StpUtil.getLoginIdAsLong()));
    }

    @Operation(summary = "【用户】修改密码", description = "修改密码")
    @Log(modul = "【用户】修改密码", type = OptConstants.UPDATE, desc = "修改密码")
    @PostMapping("/modify/password")
    public R<String> modifyPassword(@RequestBody @Validated AdminModifyPasswordParam param) {
        userService.updatePassword(StpUtil.getLoginIdAsLong(), param.getPass());
        return R.status(true);
    }

    @Operation(summary = "【用户】登出", description = "登出")
    @PostMapping("/logout")
    public R<String> logout() {
        StpUtil.logout();
        return R.status(true);
    }

    @SaCheckRole(value = {"super_admin", "admin"}, mode = SaMode.OR)
    @Operation(summary = "【用户】用户列表", description = "用户列表")
    @GetMapping("/list")
    public R<List<User>> userList(@RequestParam(value = "username", required = false) String username, @RequestParam(value = "status", required = false) Integer status,
                                  @RequestParam(value = "phone", required = false) String phone, @RequestParam("createTime[]") List<String> createTime) {
        return R.data(userService.list(username, status, phone, createTime));
    }

    @SaCheckRole(value = {"super_admin", "admin"}, mode = SaMode.OR)
    @PostMapping("/change")
    @Log(modul = "【用户】修改用户状态", type = OptConstants.UPDATE, desc = "修改用户状态")
    @Operation(summary = "【用户】修改用户状态", description = "修改用户状态")
    public R<String> changeUserState(@RequestBody @Validated AdminUserChangeStatusParam param) {
        return R.status(userService.changeStatus(param.getId(), param.getStatus()));
    }

    @SaCheckRole(value = {"super_admin", "admin"}, mode = SaMode.OR)
    @PostMapping("/reset/password")
    @Log(modul = "【用户】重置密码", type = OptConstants.UPDATE, desc = "重置密码")
    @Operation(summary = "【用户】重置密码", description = "重置密码")
    public R<String> resetPassword(@RequestBody @Validated AdminUserParam param) {
        userService.resetPassword(param.getId());
        return R.status(true);
    }
}