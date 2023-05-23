/**
 * Copyright (C), 2015-2023, 263企业通信
 * FileName: AdminCategoryController
 * Author:   Derek Xu
 * Date:     2023/5/17 9:30
 * Description:
 * History:
 * <author>          <time>          <version>          <desc>
 * 作者姓名           修改时间           版本号              描述
 */
package cn.com.xuct.group.purchase.controller.admin;

import cn.com.xuct.group.purchase.annotation.Log;
import cn.com.xuct.group.purchase.base.res.R;
import cn.com.xuct.group.purchase.constants.OptConstants;
import cn.com.xuct.group.purchase.entity.Category;
import cn.com.xuct.group.purchase.service.CategoryService;
import cn.dev33.satoken.annotation.SaCheckPermission;
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
 * @create 2023/5/17
 * @since 1.0.0
 */
@Tag(name = "【管理员-分类模块】")
@RequestMapping("/api/admin/v1/category")
@RequiredArgsConstructor
@RestController
public class AdminCategoryController {

    private final CategoryService categoryService;

    @Operation(summary = "【分类】分类列表", description = "分类列表")
    @GetMapping("")
    public R<List<Category>> list() {
        return R.data(categoryService.list());
    }

    @SaCheckPermission("category:manage:add")
    @Operation(summary = "【分类】新增分类", description = "新增分类")
    @Log(modul = "【分类】新增分类", type = OptConstants.INSERT, desc = "新增分类")
    @PostMapping("")
    public R<String> addCategory(@RequestBody @Validated Category category) {
        categoryService.save(category);
        return R.status(true);
    }

    @SaCheckPermission("category:manage:edit")
    @Operation(summary = "【分类】更新分类", description = "更新分类")
    @Log(modul = "【分类】更新分类", type = OptConstants.UPDATE, desc = "更新分类")
    @PutMapping("")
    public R<String> editCategory(@RequestBody @Validated Category category) {
        categoryService.updateById(category);
        return R.status(true);
    }

    @SaCheckPermission("category:manage:del")
    @Operation(summary = "【分类】删除分类", description = "删除分类")
    @Log(modul = "【分类】删除分类", type = OptConstants.DELETE, desc = "更新分类")
    @DeleteMapping("/{id}")
    public R<String> deleteCategory(@PathVariable("id") Long id) {
        categoryService.deleteCategory(id);
        return R.status(true);
    }
}