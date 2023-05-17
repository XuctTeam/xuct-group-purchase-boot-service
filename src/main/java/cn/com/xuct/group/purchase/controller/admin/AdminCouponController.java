/**
 * Copyright (C), 2015-2023, 263企业通信
 * FileName: AdminCouponController
 * Author:   Derek Xu
 * Date:     2023/5/17 13:35
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
import cn.com.xuct.group.purchase.entity.Coupon;
import cn.com.xuct.group.purchase.service.CouponService;
import cn.com.xuct.group.purchase.vo.param.admin.AdminCouponChangeStatusParam;
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
 * @create 2023/5/17
 * @since 1.0.0
 */
@Tag(name = "【管理员-优惠券模块】")
@RequestMapping("/api/admin/v1/coupon")
@RequiredArgsConstructor
@RestController
public class AdminCouponController {

    private final CouponService couponService;

    @Operation(summary = "【优惠券】优惠券列表", description = "优惠券列表")
    @GetMapping("")
    @Parameters(value = {
            @Parameter(name = "pageNum", description = "当前页", required = true),
            @Parameter(name = "pageSize", description = "每页条数", required = true),
            @Parameter(name = "name", description = "优惠券名称")
    })
    public R<PageData<Coupon>> pages(@RequestParam(value = "name", required = false) String name, @RequestParam("pageNum") Integer pageNum, @RequestParam("pageSize") Integer pageSize) {
        return R.data(couponService.pages(name, pageNum, pageSize));
    }

    @Operation(summary = "【优惠券】新增优惠券", description = "新增优惠券")
    @Log(modul = "【优惠券】新增优惠券", type = OptConstants.INSERT, desc = "新增分类")
    @PostMapping("")
    public R<String> addCoupon(@RequestBody @Validated Coupon coupon) {
        int result = couponService.addCoupon(coupon);
        if (result == -1) {
            return R.fail("选择商品为空");
        }
        return R.status(true);
    }

    @Operation(summary = "【优惠券】编辑优惠券", description = "编辑优惠券")
    @Log(modul = "【优惠券】编辑优惠券", type = OptConstants.UPDATE, desc = "编辑优惠券")
    @PutMapping("")
    public R<String> editCoupon(@RequestBody @Validated Coupon coupon) {
        int result = couponService.editCoupon(coupon);
        if (result == -1) {
            return R.fail("选择商品为空");
        }
        return R.status(true);
    }

    @Operation(summary = "【优惠券】修改状态", description = "新增优惠修改状态券")
    @Log(modul = "【优惠券】修改状态", type = OptConstants.UPDATE, desc = "修改状态")
    @PostMapping("/change/status")
    public R<String> changeStatus(@RequestBody @Validated AdminCouponChangeStatusParam param) {
        couponService.changeCouponStatus(param.getId(), param.getStatus());
        return R.status(true);
    }

    @Operation(summary = "【优惠券】删除优惠券", description = "删除优惠券")
    @Log(modul = "【优惠券】删除优惠券", type = OptConstants.UPDATE, desc = "删除优惠券")
    @DeleteMapping("/{id}")
    public R<String> deleteCoupon(@PathVariable("id") Long id) {
        couponService.deleteCoupon(id);
        return R.status(true);
    }
}