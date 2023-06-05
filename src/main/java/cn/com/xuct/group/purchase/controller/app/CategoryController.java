/**
 * Copyright (C), 2015-2023, 263企业通信
 * FileName: CategoryController
 * Author:   Derek Xu
 * Date:     2023/6/5 9:55
 * Description:
 * History:
 * <author>          <time>          <version>          <desc>
 * 作者姓名           修改时间           版本号              描述
 */
package cn.com.xuct.group.purchase.controller.app;

import cn.com.xuct.group.purchase.base.enums.SortEnum;
import cn.com.xuct.group.purchase.base.res.R;
import cn.com.xuct.group.purchase.base.vo.Sort;
import cn.com.xuct.group.purchase.entity.Category;
import cn.com.xuct.group.purchase.service.CategoryService;
import cn.dev33.satoken.annotation.SaIgnore;
import com.google.common.collect.Lists;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * 〈一句话功能简述〉<br>
 * 〈〉
 *
 * @author Derek Xu
 * @create 2023/6/5
 * @since 1.0.0
 */
@Tag(name = "【商品分类模块】")
@RequestMapping("/api/v1/category")
@RestController
@Slf4j
@RequiredArgsConstructor
public class CategoryController {

    private final CategoryService categoryService;

    @SaIgnore
    @GetMapping("/list")
    @Operation(summary = "【商品分类】列表", description = "查询商品分类列表")
    public R<List<Category>> list() {
        return R.data(categoryService.find(Lists.newArrayList(), Sort.of("sort", SortEnum.asc)));
    }
}