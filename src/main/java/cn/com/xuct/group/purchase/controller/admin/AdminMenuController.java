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
import cn.com.xuct.group.purchase.entity.Resource;
import cn.com.xuct.group.purchase.service.ResourceService;
import cn.com.xuct.group.purchase.vo.result.admin.AdminMenuResult;
import cn.com.xuct.group.purchase.vo.result.admin.AdminMenuTreeResult;
import cn.dev33.satoken.annotation.SaCheckRole;
import cn.dev33.satoken.annotation.SaMode;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * 〈一句话功能简述〉<br>
 * 〈〉
 *
 * @author Derek Xu
 * @create 2023/5/10
 * @since 1.0.0
 */
@Tag(name = "【管理员-资源模块】")
@RequestMapping("/api/admin/v1/menu")
@RequiredArgsConstructor
@RestController
@SaCheckRole(value = {"super_admin", "admin"}, mode = SaMode.OR)
public class AdminMenuController {

    private final ResourceService resourceService;

    @Operation(summary = "【资源】资源树", description = "资源树")
    @GetMapping("/tree")
    public R<List<AdminMenuTreeResult>> menuTreeList(@RequestParam(value = "showBtn", required = false) Integer showBtn) {
        return R.data(resourceService.menuTreeList(showBtn));
    }

    @Operation(summary = "【资源】资源列表", description = "资源列表")
    @GetMapping("")
    public R<List<AdminMenuResult>> list() {
        return R.data(resourceService.menuList());
    }

    @Operation(summary = "【资源】添加资源", description = "添加资源")
    @PostMapping
    public R<String> addMenu(@RequestBody Resource resource) {
        return R.status(resourceService.addOrUpdateResource(resource));
    }

    @Operation(summary = "【资源】修改资源", description = "修改资源")
    @PutMapping
    public R<String> editMenu(@RequestBody Resource resource) {
        return R.status(resourceService.addOrUpdateResource(resource));
    }

    @Operation(summary = "【资源】删除资源", description = "删除资源")
    @DeleteMapping("/{resourceId}")
    public R<String> deleteMenu(@PathVariable("resourceId") Long resourceId) {
        return R.status(resourceService.deleteResource(resourceId));
    }
}