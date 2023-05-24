/**
 * Copyright (C), 2015-2023, 263企业通信
 * FileName: AdminMemberOrderController
 * Author:   Derek Xu
 * Date:     2023/5/22 17:54
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
import cn.com.xuct.group.purchase.service.MemberOrderService;
import cn.com.xuct.group.purchase.vo.result.OrderResult;
import cn.dev33.satoken.annotation.SaCheckPermission;
import cn.hutool.db.sql.Order;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.Parameters;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
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
@Tag(name = "【管理员-订单模块】")
@RequestMapping("/api/admin/v1/order")
@RequiredArgsConstructor
@RestController
public class AdminMemberOrderController {

    private final MemberOrderService memberOrderService;

    @Operation(summary = "【订单】订单列表", description = "订单列表")
    @Parameters(value = {
            @Parameter(name = "pageNum", description = "当前页", required = true),
            @Parameter(name = "pageSize", description = "每页条数", required = true),
            @Parameter(name = "createTime", description = "注册时间"),
            @Parameter(name = "status", description = "状态")
    })
    @GetMapping("/list")
    public R<PageData<OrderResult>> list(
            @RequestParam("createTime[]") List<String> createTime,
            @RequestParam(value = "status", required = false) Integer status,
            @RequestParam(value = "pageNum", defaultValue = "1") Integer pageNum,
            @RequestParam(value = "pageSize", defaultValue = "10") Integer pageSize) {
        return R.data(memberOrderService.findAllMemberOrder(status, createTime, pageNum, pageSize));
    }

    @Operation(summary = "【订单】获取详情", description = "获取详情")
    @GetMapping("/{id}")
    public R<OrderResult> detail(@PathVariable(name = "id") Long id) {
        return R.data(memberOrderService.getDetail(id, null));
    }

    @SaCheckPermission("members:order:deliver")
    @Operation(summary = "【订单】发货", description = "发货")
    @Log(modul = "【订单】发货", type = OptConstants.UPDATE, desc = "发货")
    @PutMapping("/deliver/{id}")
    public R<String> deliverOrder(@PathVariable(name = "id") Long id) {
        memberOrderService.deliverOrder(id);
        return R.success("发货成功");
    }
}