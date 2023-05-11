/**
 * Copyright (C), 2015-2023, 263企业通信
 * FileName: AdminRoleController
 * Author:   Derek Xu
 * Date:     2023/5/11 11:19
 * Description:
 * History:
 * <author>          <time>          <version>          <desc>
 * 作者姓名           修改时间           版本号              描述
 */
package cn.com.xuct.group.purchase.controller.admin;

import cn.com.xuct.group.purchase.base.res.R;
import cn.com.xuct.group.purchase.entity.Role;
import cn.com.xuct.group.purchase.service.RoleService;
import cn.com.xuct.group.purchase.vo.param.RoleResourceIdsParam;
import cn.dev33.satoken.annotation.SaCheckRole;
import cn.dev33.satoken.annotation.SaMode;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * 〈一句话功能简述〉<br>
 * 〈〉
 *
 * @author Derek Xu
 * @create 2023/5/11
 * @since 1.0.0
 */
@Tag(name = "【管理员-菜单模块】")
@RequestMapping("/api/admin/v1/role")
@RequiredArgsConstructor
@RestController
@SaCheckRole(value = {"super_admin", "admin"}, mode = SaMode.OR)
public class AdminRoleController {

    private final RoleService roleService;

    @Operation(summary = "【角色】角色列表", description = "角色列表")
    @GetMapping("/list")
    public R<List<Role>> list() {
        return R.data(roleService.findRoleList());
    }

    @Operation(summary = "【角色】角色对应资源ID", description = "角色对应资源ID")
    @GetMapping("/resource")
    public R<List<String>> getRoleResourceIds(@RequestParam("roleId") Long roleId) {
        return R.data(roleService.getRoleResourceIds(roleId));
    }

    @Operation(summary = "【角色】新增或修改角色", description = "新增或修改角色")
    @PostMapping("")
    public R<String> saveOrUpdate(@RequestBody Role role) {
        roleService.saveOrUpdate(role);
        return R.status(true);
    }

    @Operation(summary = "【角色】角色绑定资源", description = "角色绑定资源")
    @PostMapping("/resource")
    public R<String> bindRoleResourceIds(@RequestBody @Validated RoleResourceIdsParam param) {
        roleService.bindRoleResourceIds(param.getRoleId(), param.getResourceIds());
        return R.status(true);
    }

}