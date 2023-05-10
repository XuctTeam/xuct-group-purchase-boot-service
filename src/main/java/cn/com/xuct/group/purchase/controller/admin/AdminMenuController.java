/**
 * Copyright (C), 2015-2023, 263企业通信
 * FileName: AdminMenuController
 * Author:   Derek Xu
 * Date:     2023/5/10 11:33
 * Description:
 * History:
 * <author>          <time>          <version>          <desc>
 * 作者姓名           修改时间           版本号              描述
 */
package cn.com.xuct.group.purchase.controller.admin;

import cn.com.xuct.group.purchase.base.res.R;
import cn.com.xuct.group.purchase.vo.result.admin.AdminMenuTreeResult;
import cn.dev33.satoken.annotation.SaCheckRole;
import cn.dev33.satoken.annotation.SaMode;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * 〈一句话功能简述〉<br> 
 * 〈〉
 *
 * @author Derek Xu
 * @create 2023/5/10
 * @since 1.0.0
 */
@Tag(name = "【管理员-菜单模块】")
@RequestMapping("/api/admin/v1/menu")
@RequiredArgsConstructor
@RestController
@SaCheckRole(value = {"super_admin", "admin"}, mode = SaMode.OR)
public class AdminMenuController {


    @Operation(summary = "【菜单】菜单树", description = "菜单树")
    @GetMapping("/menu")
    public R<List<AdminMenuTreeResult>> menuTreeList() {
        return null;
    }
}