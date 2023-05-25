/**
 * Copyright (C), 2015-2023, 263企业通信
 * FileName: AdminMemberController
 * Author:   Derek Xu
 * Date:     2023/5/22 9:06
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
import cn.com.xuct.group.purchase.entity.Member;
import cn.com.xuct.group.purchase.service.MemberCouponService;
import cn.com.xuct.group.purchase.service.MemberService;
import cn.com.xuct.group.purchase.vo.param.admin.AdminMemberCouponParam;
import cn.com.xuct.group.purchase.vo.param.admin.AdminMemberStatusParam;
import cn.dev33.satoken.annotation.SaCheckPermission;
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
 * @create 2023/5/22
 * @since 1.0.0
 */
@Tag(name = "【管理员-会员模块】")
@RequestMapping("/api/admin/v1/member")
@RequiredArgsConstructor
@RestController
public class AdminMemberController {

    private final MemberService memberService;

    private final MemberCouponService memberCouponService;

    @Operation(summary = "【会员】会员列表", description = "会员列表")
    @Parameters(value = {
            @Parameter(name = "pageNum", description = "当前页", required = true),
            @Parameter(name = "pageSize", description = "每页条数", required = true),
            @Parameter(name = "createTime", description = "注册时间"),
            @Parameter(name = "nickname", description = "昵称"),
            @Parameter(name = "status", description = "状态")
    })
    @GetMapping("/list")
    public R<PageData<Member>> list(@RequestParam(value = "nickname", required = false) String nickname,
                                    @RequestParam("createTime[]") List<String> createTime,
                                    @RequestParam(value = "status" , required = false)Integer status,
                                    @RequestParam("pageNum") Integer pageNum, @RequestParam("pageSize") Integer pageSize) {

        return R.data(memberService.findPageList(nickname, status , createTime, pageNum, pageSize));
    }

    @Operation(summary = "【会员】修改状态", description = "修改状态")
    @Log(modul = "【会员】修改状态", type = OptConstants.UPDATE, desc = "修改状态")
    @PostMapping("/change/status")
    public R<String> changeMemberStatus(@RequestBody @Validated AdminMemberStatusParam param) {
        memberService.changeMemberStatus(param.getId(), param.getStatus());
        return R.status(true);
    }

    @SaCheckPermission("members:coupon:push")
    @Operation(summary = "【会员】发放优惠券", description = "发放优惠券")
    @Log(modul = "【会员】发放优惠券", type = OptConstants.INSERT, desc = "发放优惠券")
    @PostMapping("/coupon")
    public R<String> addMemberCoupon(@RequestBody @Validated AdminMemberCouponParam param) {
        int result = memberCouponService.addMemberCoupon(param.getMemberId(), param.getCouponId(), param.getTimes());
        return switch (result) {
            case -1 -> R.fail("优惠券不存在");
            case -2 -> R.fail("已达到最大发放数量");
            default -> R.status(true);
        };
    }
}