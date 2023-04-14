/**
 * Copyright (C), 2015-2023, 263企业通信
 * FileName: UserOrderController
 * Author:   Derek Xu
 * Date:     2023/4/10 9:35
 * Description:
 * History:
 * <author>          <time>          <version>          <desc>
 * 作者姓名           修改时间           版本号              描述
 */
package cn.com.xuct.group.purchase.controller;

import cn.com.xuct.group.purchase.base.res.R;
import cn.com.xuct.group.purchase.base.vo.PageData;
import cn.com.xuct.group.purchase.constants.OrderResultConstants;
import cn.com.xuct.group.purchase.entity.UserOrder;
import cn.com.xuct.group.purchase.service.UserOrderService;
import cn.com.xuct.group.purchase.vo.param.OrderParam;
import cn.dev33.satoken.stp.StpUtil;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.Parameters;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.util.StringUtils;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

/**
 * 〈一句话功能简述〉<br>
 * 〈〉
 *
 * @author Derek Xu
 * @create 2023/4/10
 * @since 1.0.0
 */
@Tag(name = "【订单模块】")
@RequestMapping("/api/v1/order")
@RestController
@Slf4j
@RequiredArgsConstructor
public class UserOrderController {

    private final UserOrderService userOrderService;

    @Operation(summary = "下订单")
    @PostMapping
    public R<String> place(@Validated @RequestBody OrderParam param) {
        String result = userOrderService.saveOrder(StpUtil.getLoginIdAsLong(), param.getAddressId(), param.getIntegral(), param.getRemarks(), param.getGoodIds());
        return switch (result) {
            case OrderResultConstants.CART_EMPTY -> R.fail("购买商品错误");
            case OrderResultConstants.NOT_ENOUGH -> R.fail("库存不足");
            case OrderResultConstants.ERROR -> R.fail("下单异常");
            default -> R.data(result);
        };
    }

    @Operation(summary = "订单列表")
    @GetMapping("/list")
    @Parameters(value = {
            @Parameter(name = "status", description = "状态"),
            @Parameter(name = "pageNo", description = "页码", required = true),
            @Parameter(name = "pageSize", description = "每页条数", required = true)
    })
    public R<PageData<UserOrder>> list(@RequestParam("status") Integer status, @RequestParam("pageNo") Integer pageNo, @RequestParam("pageSize") Integer pageSize) {
        return R.data(userOrderService.convert(userOrderService.list(StpUtil.getLoginIdAsLong(), status == 0 ? null : status, pageNo, pageSize)));
    }
}