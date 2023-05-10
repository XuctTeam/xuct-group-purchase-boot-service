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

import cn.com.xuct.group.purchase.base.res.R;
import cn.com.xuct.group.purchase.service.RoleService;
import cn.com.xuct.group.purchase.service.UserService;
import cn.com.xuct.group.purchase.vo.param.AdminModifyPasswordParam;
import cn.com.xuct.group.purchase.vo.result.admin.AdminMenuResult;
import cn.com.xuct.group.purchase.vo.result.admin.AdminMenuTreeResult;
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
@SaCheckRole(value = {"super_admin", "admin"}, mode = SaMode.OR)
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
}