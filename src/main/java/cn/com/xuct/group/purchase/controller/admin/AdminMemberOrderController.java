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

import cn.com.xuct.group.purchase.base.res.R;
import cn.com.xuct.group.purchase.base.vo.PageData;
import cn.com.xuct.group.purchase.service.MemberOrderService;
import cn.com.xuct.group.purchase.vo.result.OrderResult;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.Parameters;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

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
            @Parameter(name = "pageSize", description = "每页条数", required = true)
    })
    public R<PageData<OrderResult>> list(@RequestParam(value = "pageNum", defaultValue = "1") Integer pageNum,
                                         @RequestParam(value = "pageSize", defaultValue = "10") Integer pageSize) {
        return R.data(memberOrderService.findAllMemberOrder(pageNum, pageSize));
    }

}