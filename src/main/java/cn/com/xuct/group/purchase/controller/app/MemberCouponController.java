/**
 * Copyright (C), 2015-2023, 楚恬商场
 * FileName: UserCouponController
 * Author:   Derek Xu
 * Date:     2023/4/26 19:31
 * Description:
 * History:
 * <author>          <time>          <version>          <desc>
 * Derek Xu           修改时间           版本号              描述
 */
package cn.com.xuct.group.purchase.controller.app;

import cn.com.xuct.group.purchase.base.res.R;
import cn.com.xuct.group.purchase.entity.MemberCoupon;
import cn.com.xuct.group.purchase.service.MemberCouponService;
import cn.dev33.satoken.stp.StpUtil;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.Parameters;
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
 * @create 2023/4/26
 * @since 1.0.0
 */
@Tag(name = "【优惠券模块】")
@RequestMapping("/api/v1/coupon")
@RestController
@Slf4j
@RequiredArgsConstructor
public class MemberCouponController {

    private final MemberCouponService memberCouponService;

    @GetMapping("/list")
    @Operation(summary = "【优惠券】优惠券列表", description = "优惠券列表")
    @Parameters(value = {
            @Parameter(name = "status", description = "使用状态 0未使用 1使用 2已过期"),
    })
    public R<List<MemberCoupon>> list(@RequestParam("status") Integer status) {
        return R.data(memberCouponService.list(StpUtil.getLoginIdAsLong(), status));
    }


    @GetMapping("/can/used")
    @Operation(summary = "【优惠券】可使用的优惠券列表", description = "可使用的优惠券列表")
    public R<List<MemberCoupon>> canUsed() {
        return R.data(memberCouponService.canUsed(StpUtil.getLoginIdAsLong()));
    }
}