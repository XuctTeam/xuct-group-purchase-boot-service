/**
 * Copyright (C), 2015-2023, 263企业通信
 * FileName: AdminBannerController
 * Author:   Derek Xu
 * Date:     2023/5/17 11:13
 * Description:
 * History:
 * <author>          <time>          <version>          <desc>
 * 作者姓名           修改时间           版本号              描述
 */
package cn.com.xuct.group.purchase.controller.admin;

import cn.com.xuct.group.purchase.annotation.Log;
import cn.com.xuct.group.purchase.base.res.R;
import cn.com.xuct.group.purchase.constants.OptConstants;
import cn.com.xuct.group.purchase.entity.Banner;
import cn.com.xuct.group.purchase.service.BannerService;
import cn.com.xuct.group.purchase.vo.param.admin.AdminBannerStatusParam;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.Parameters;
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
@Tag(name = "【管理员-轮播图模块】")
@RequestMapping("/api/admin/v1/banner")
@RequiredArgsConstructor
@RestController
public class AdminBannerController {

    private final BannerService bannerService;

    @Operation(summary = "【轮播图】轮播图列表", description = "轮播图列表")
    @GetMapping("")
    @Parameters(value = {
            @Parameter(name = "title", description = "标题"),
            @Parameter(name = "status", description = "状态")
    })
    public R<List<Banner>> list(@RequestParam(value = "title", required = false) String title, @RequestParam(value = "status", required = false) Integer status) {
        return R.data(bannerService.list(title, status));
    }

    @Operation(summary = "【轮播图】新增轮播图", description = "新增轮播图")
    @Log(modul = "【轮播图】新增轮播图", type = OptConstants.INSERT, desc = "新增轮播图")
    @PostMapping("")
    public R<String> addBanner(@RequestBody @Validated Banner banner) {
        bannerService.save(banner);
        return R.status(true);
    }

    @Operation(summary = "【轮播图】 编辑轮播图", description = "编辑轮播图")
    @Log(modul = "【轮播图】编辑轮播图", type = OptConstants.UPDATE, desc = "编辑轮播图")
    @PutMapping("")
    public R<String> editBanner(@RequestBody @Validated Banner banner) {
        bannerService.updateById(banner);
        return R.status(true);
    }


    @Operation(summary = "【轮播图】 修改轮播图状态", description = "修改轮播图状态")
    @Log(modul = "【轮播图】修改轮播图状态", type = OptConstants.UPDATE, desc = "修改轮播图状态")
    @PostMapping("/change/status")
    public R<String> changeBannerStatus(@RequestBody @Validated AdminBannerStatusParam param) {
        return R.status(bannerService.changeBannerStatus(param.getId(), param.getStatus()));
    }

    @Operation(summary = "【轮播图】 删除轮播图", description = "删除轮播图")
    @Log(modul = "【轮播图】删除轮播图", type = OptConstants.DELETE, desc = "删除轮播图")
    @DeleteMapping("/{id}")
    public R<String> deleteBanner(@PathVariable("id") Long id) {
        bannerService.removeById(id);
        return R.status(true);
    }
}