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
import cn.com.xuct.group.purchase.client.cos.client.CosClient;
import cn.com.xuct.group.purchase.constants.FileFolderConstants;
import cn.com.xuct.group.purchase.constants.RConstants;
import cn.com.xuct.group.purchase.entity.UserOrder;
import cn.com.xuct.group.purchase.service.UserOrderService;
import cn.com.xuct.group.purchase.vo.param.CartManyGoodParam;
import cn.com.xuct.group.purchase.vo.param.OrderIdParam;
import cn.com.xuct.group.purchase.vo.param.OrderParam;
import cn.com.xuct.group.purchase.vo.param.RefundOrderParam;
import cn.com.xuct.group.purchase.vo.result.CartResult;
import cn.com.xuct.group.purchase.vo.result.OrderResult;
import cn.com.xuct.group.purchase.vo.result.OrderSumResult;
import cn.dev33.satoken.stp.StpUtil;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.Parameters;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.util.List;
import java.util.Objects;

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

    @Operation(summary = "【订单】统计总数", description = "统计总数")
    @GetMapping("/sum")
    public R<OrderSumResult> sumCount() {
        return R.data(userOrderService.sumCount(StpUtil.getLoginIdAsLong()));
    }

    @Operation(summary = "【订单】确认订单详情", description = "确认订单详情")
    @PostMapping("/confirm/detail")
    public R<List<CartResult>> getConfirmOrderDetail(@RequestBody @Validated CartManyGoodParam param) {
        return R.data(userOrderService.getConfirmOrderDetail(StpUtil.getLoginIdAsLong(), param.getScene(), param.getGids()));
    }

    @Operation(summary = "【订单】下订单", description = "下订单")
    @PostMapping
    public R<String> place(@Validated @RequestBody OrderParam param) {
        String result = userOrderService.saveOrder(StpUtil.getLoginIdAsLong(), param.getScene(), param.getAddressId(), param.getIntegral(), param.getRemarks(), param.getGoodIds());
        return switch (result) {
            case RConstants.CART_EMPTY -> R.fail("购买商品错误！");
            case RConstants.NOT_ENOUGH -> R.fail("库存不足！");
            case RConstants.ORDER_SCENE_ERROR -> R.fail("订单方式不支持！");
            case RConstants.ERROR -> R.fail("下单异常！");
            default -> R.data(result);
        };
    }

    @Operation(summary = "【订单】订单列表", description = "订单列表")
    @GetMapping("/list")
    @Parameters(value = {
            @Parameter(name = "status", description = "状态"),
            @Parameter(name = "pageNo", description = "页码", required = true),
            @Parameter(name = "pageSize", description = "每页条数", required = true)
    })
    public R<PageData<UserOrder>> list(@RequestParam("status") Integer status, @RequestParam("pageNo") Integer pageNo, @RequestParam("pageSize") Integer pageSize) {
        return R.data(userOrderService.convert(userOrderService.list(StpUtil.getLoginIdAsLong(), status == 0 ? null : status, pageNo, pageSize)));
    }

    @Operation(summary = "【订单】订单详情", description = "订单详情")
    @GetMapping("/")
    @Parameters(value = {
            @Parameter(name = "orderId", description = "订单ID"),
    })
    public R<OrderResult> getDetail(@RequestParam("orderId") String orderId) {
        return R.data(userOrderService.getDetail(StpUtil.getLoginIdAsLong(), Long.valueOf(orderId)));
    }

    @Operation(summary = "【订单】退单上传图片", description = "退单上传图片")
    @PostMapping("/refund/upload")
    public R<String> uploadRefundImage(MultipartFile file) {
        try {
            URL url = CosClient.uploadFile(file, FileFolderConstants.REFUND.concat(Objects.requireNonNull(file.getOriginalFilename())));
            return R.data(url.toString());
        } catch (IOException e) {
            log.error("UserOrderController:: upload error");
            return R.fail("上传失败");
        }
    }

    @Operation(summary = "【订单】申请退单", description = "申请退单")
    @PostMapping("/refund")
    public R<String> refundOrder(@RequestBody @Validated RefundOrderParam param) {
        String result = userOrderService.refundOrder(StpUtil.getLoginIdAsLong(), Long.valueOf(param.getOrderId()), param.getRefundType(), param.getRefundReason(), param.getRefundImages());
        return switch (result) {
            case RConstants.ORDER_NOT_EXIST -> R.fail("订单不存在！");
            case RConstants.ORDER_ALREADY_REFUND -> R.fail("订单已经申请退款！");
            case RConstants.ERROR -> R.fail("申请失败！");
            default -> R.data(result);
        };
    }

    @Operation(summary = "【订单】催单", description = "催单")
    @PostMapping("/rush")
    public R<String> rushOrder(@RequestBody @Validated OrderIdParam param) {
        userOrderService.rushOrder(StpUtil.getLoginIdAsLong(), Long.valueOf(param.getOrderId()));
        return R.status(true);
    }

    @Operation(summary = "【订单】收货", description = "收货")
    @PostMapping("/receive")
    public R<String> receiveOrder(@RequestBody @Validated OrderIdParam param) {
        userOrderService.receiveOrder(StpUtil.getLoginIdAsLong(), Long.valueOf(param.getOrderId()));
        return R.status(true);
    }

    @Operation(summary = "【订单】删除订单", description = "删除订单")
    @DeleteMapping
    public R<String> deleteOrder(@RequestBody @Validated OrderIdParam param) {
        userOrderService.deleteOrder(StpUtil.getLoginIdAsLong(), Long.valueOf(param.getOrderId()));
        return R.status(true);
    }
}