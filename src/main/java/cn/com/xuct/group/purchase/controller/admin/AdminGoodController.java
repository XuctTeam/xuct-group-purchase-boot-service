/**
 * Copyright (C), 2015-2023, 263企业通信
 * FileName: AdminGoodController
 * Author:   Derek Xu
 * Date:     2023/5/15 16:11
 * Description:
 * History:
 * <author>          <time>          <version>          <desc>
 * 作者姓名           修改时间           版本号              描述
 */
package cn.com.xuct.group.purchase.controller.admin;

import cn.com.xuct.group.purchase.annotation.Log;
import cn.com.xuct.group.purchase.base.res.R;
import cn.com.xuct.group.purchase.base.vo.PageData;
import cn.com.xuct.group.purchase.constants.OptConstants;
import cn.com.xuct.group.purchase.entity.Good;
import cn.com.xuct.group.purchase.service.GoodService;
import cn.com.xuct.group.purchase.vo.param.admin.AdminGoodStatusParam;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.Parameters;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

/**
 * 〈一句话功能简述〉<br>
 * 〈〉
 *
 * @author Derek Xu
 * @create 2023/5/15
 * @since 1.0.0
 */
@Tag(name = "【管理员-商品模块】")
@RequestMapping("/api/admin/v1/good")
@RequiredArgsConstructor
@RestController
public class AdminGoodController {


    private final GoodService goodService;

    @Operation(summary = "【商品】商品列表", description = "商品列表")
    @GetMapping("")
    @Parameters(value = {
            @Parameter(name = "pageNum", description = "当前页", required = true),
            @Parameter(name = "pageSize", description = "每页条数", required = true),
            @Parameter(name = "module", description = "操作模块"),
            @Parameter(name = "createTime", description = "操作时间")
    })
    public R<PageData<Good>> list(@RequestParam(value = "name", required = false) String name, @RequestParam("pageNum") Integer pageNum, @RequestParam("pageSize") Integer pageSize) {
        return R.data(goodService.pageGoods(name, pageNum, pageSize));
    }


    @Operation(summary = "【商品】修改商品状态", description = "修改商品状态")
    @PostMapping("/change/status")
    @Log(modul = "【商品】修改商品状态", type = OptConstants.UPDATE, desc = "修改商品状态")
    public R<String> changeGoodStatus(@RequestBody @Validated AdminGoodStatusParam param) {
        goodService.changeGoodStatus(param.getId(), param.getStatus());
        return R.status(true);
    }

    @Operation(summary = "【商品】删除商品", description = "删除商品")
    @Log(modul = "【商品】删除商品", type = OptConstants.DELETE, desc = "删除商品")
    @DeleteMapping("/{id}")
    public R<String> deleteGood(@PathVariable("id") Long id) {
        goodService.deleteGood(id);
        return R.status(true);
    }

    @Operation(summary = "【商品】新增商品", description = "新增商品")
    @PostMapping("")
    @Log(modul = "【商品】新增商品", type = OptConstants.INSERT, desc = "新增商品")
    public R<String> addGood(@RequestBody @Validated Good good) {
        int result = goodService.addGood(good);
        if (result == -1) {
            return R.fail("商品结束时间不能小于当前时间");
        }
        return R.status(true);
    }

    @Operation(summary = "【商品】编辑商品", description = "编辑商品")
    @PutMapping("")
    @Log(modul = "【商品】编辑商品", type = OptConstants.UPDATE, desc = "编辑商品")
    public R<String> editGood(@RequestBody @Validated Good good) {
        int result = goodService.editGood(good);
        return switch (result) {
            case -1 -> R.fail("商品结束时间不能小于当前时间");
            case -2 -> R.fail("商品未找到");
            default -> R.status(true);
        };
    }
}