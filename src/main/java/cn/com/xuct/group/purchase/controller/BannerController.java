/**
 * Copyright (C), 2015-2023, XXX有限公司
 * FileName: BannerController
 * Author:   Derek Xu
 * Date:     2023/3/22 10:41
 * Description:
 * History:
 * <author>          <time>          <version>          <desc>
 * Derek Xu         修改时间           版本号              描述
 */
package cn.com.xuct.group.purchase.controller;

import cn.com.xuct.group.purchase.base.res.R;
import cn.com.xuct.group.purchase.entity.Banner;
import cn.com.xuct.group.purchase.service.BannerService;
import cn.dev33.satoken.annotation.SaIgnore;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

/**
 * 〈一句话功能简述〉<br> 
 * 〈〉
 *
 * @author Derek Xu
 * @create 2023/3/22
 * @since 1.0.0
 */
@Tag(name = "【轮播图模块】")
@RequestMapping("/api/v1/banner")
@RestController
@Slf4j
@RequiredArgsConstructor
@SaIgnore
public class BannerController {

    private final BannerService bannerService;

    @GetMapping("/list")
    @Operation(summary = "获取轮播图", description = "获取轮播图")
    public R<List<Banner>> list(){
        return R.data(bannerService.list().stream().sorted(Comparator.comparing(Banner::getSort).reversed()).collect(Collectors.toList()));
    }

    @GetMapping
    @Operation(summary = "获取轮播详情", description = "获取轮播详情")
    public R<Banner> get(@RequestParam("id")Long id){
        return R.data(bannerService.getById(id));
    }
}