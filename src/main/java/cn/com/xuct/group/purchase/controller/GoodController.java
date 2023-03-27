/**
 * Copyright (C), 2015-2023, XXX有限公司
 * FileName: GoodController
 * Author:   Derek Xu
 * Date:     2023/3/27 11:27
 * Description:
 * History:
 * <author>          <time>          <version>          <desc>
 * Derek Xu         修改时间           版本号              描述
 */
package cn.com.xuct.group.purchase.controller;

import cn.com.xuct.group.purchase.base.res.R;
import cn.com.xuct.group.purchase.base.vo.Column;
import cn.com.xuct.group.purchase.entity.Good;
import cn.com.xuct.group.purchase.service.GoodService;
import cn.dev33.satoken.annotation.SaIgnore;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * 〈一句话功能简述〉<br>
 * 〈〉
 *
 * @author Derek Xu
 * @create 2023/3/27
 * @since 1.0.0
 */
@Tag(name = "【商品模块】")
@RequestMapping("/api/v1/good")
@RestController
@Slf4j
@RequiredArgsConstructor
@SaIgnore
public class GoodController {

    private final GoodService goodService;

    @Operation(summary = "获取商品", description = "获取商品")
    @GetMapping("/list")
    public R<List<Good>> list() {
        return R.data(goodService.findList());
    }


    @Operation(summary = "获取详情", description = "获取详情")
    @GetMapping
    public R<Good> get(@RequestParam("id") Long id) {
        return R.data(goodService.getById(id));
    }
}