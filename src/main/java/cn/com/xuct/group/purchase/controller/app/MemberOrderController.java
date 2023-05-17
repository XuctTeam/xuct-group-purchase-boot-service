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
package cn.com.xuct.group.purchase.controller.app;

import cn.com.xuct.group.purchase.base.res.R;
import cn.com.xuct.group.purchase.base.vo.PageData;
import cn.com.xuct.group.purchase.client.cos.client.CosClient;
import cn.com.xuct.group.purchase.constants.FileFolderConstants;
import cn.com.xuct.group.purchase.constants.RConstants;
import cn.com.xuct.group.purchase.entity.MemberOrder;
import cn.com.xuct.group.purchase.entity.MemberOrderItem;
import cn.com.xuct.group.purchase.service.MemberOrderService;
import cn.com.xuct.group.purchase.vo.param.*;
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

import java.io.IOException;
import java.net.URL;
import java.util.List;
import java.util.Objects;

import static cn.com.xuct.group.purchase.constants.RConstants.*;

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
public class MemberOrderController {

    private final MemberOrderService memberOrderService;

    @Operation(summary = "【订单】统计总数", description = "统计总数")
    @GetMapping("/sum")
    public R<OrderSumResult> sumCount() {
        return R.data(memberOrderService.sumCount(StpUtil.getLoginIdAsLong()));
    }

    @Operation(summary = "【订单】确认订单详情", description = "确认订单详情")
    @PostMapping("/confirm/detail")
    public R<List<CartResult>> getConfirmOrderDetail(@RequestBody @Validated CartManyWaresParam param) {
        return R.data(memberOrderService.getConfirmOrderDetail(StpUtil.getLoginIdAsLong(), param.getScene(), param.getWaresIds()));
    }

    @Operation(summary = "【订单】下订单", description = "下订单")
    @PostMapping
    public R<String> place(@Validated @RequestBody OrderParam param) {
        String result = memberOrderService.saveOrder(StpUtil.getLoginIdAsLong(), param.getScene(), param.getAddressId(), param.getCouponId(), param.getIntegral(), param.getRemarks(), param.getWaresIds());
        return switch (result) {
            case USER_NOT_EXIST -> R.fail("用户不存在");
            case CART_EMPTY -> R.fail("购买商品错误！");
            case NOT_ENOUGH -> R.fail("库存不足！");
            case USER_INTEGRAL_NOT_ENOUGH -> R.fail("积分不足");
            case COUPON_NOT_EXIST -> R.fail("优惠券不存在");
            case ORDER_SCENE_ERROR -> R.fail("订单方式不支持！");
            case ERROR -> R.fail("下单异常！");
            default -> R.data(result);
        };
    }

    @Operation(summary = "【订单】订单列表", description = "订单列表")
    @GetMapping("/list")
    @Parameters(value = {
            @Parameter(name = "status", description = "状态"),
            @Parameter(name = "pageNo", description = "页码", required = true),
            @Parameter(name = "pageSize", description = "每页条数", required = true),
            @Parameter(name = "refundStatus", description = "退单状态"),
            @Parameter(name = "word", description = "关键词")
    })
    public R<PageData<MemberOrder>> list(@RequestParam("status") Integer status, @RequestParam("pageNo") Integer pageNo, @RequestParam("pageSize") Integer pageSize,
                                         @RequestParam(required = false, name = "refundStatus") Integer refundStatus) {
        if (status == 0) {
            status = null;
        }
        if (refundStatus != null && refundStatus == 0) {
            refundStatus = null;
        }
        return R.data(memberOrderService.convert(memberOrderService.list(StpUtil.getLoginIdAsLong(), status, pageNo, pageSize, refundStatus)));
    }

    @Operation(summary = "【订单】搜索列表", description = "搜索列表")
    @GetMapping("/search")
    @Parameters(value = {
            @Parameter(name = "word", description = "关键词"),
            @Parameter(name = "pageNo", description = "页码", required = true),
            @Parameter(name = "pageSize", description = "每页条数", required = true),
            @Parameter(name = "refund", description = "是否为售后", example = "0不是 1是", required = true),
            @Parameter(name = "word", description = "关键词")
    })
    public R<PageData<MemberOrder>> search(@RequestParam("pageNo") Integer pageNo, @RequestParam("pageSize") Integer pageSize, @RequestParam("refund") Integer refund, @RequestParam("word") String word) {
        return R.data(memberOrderService.convert(memberOrderService.search(StpUtil.getLoginIdAsLong(), pageNo, pageSize, refund, word)));
    }

    @Operation(summary = "【订单】订单详情", description = "订单详情")
    @GetMapping("/")
    @Parameters(value = {
            @Parameter(name = "orderId", description = "订单ID"),
    })
    public R<OrderResult> getDetail(@RequestParam("orderId") String orderId) {
        return R.data(memberOrderService.getDetail(StpUtil.getLoginIdAsLong(), Long.valueOf(orderId)));
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
        String result = memberOrderService.refundOrder(StpUtil.getLoginIdAsLong(), Long.valueOf(param.getOrderId()), param.getRefundType(), param.getRefundReason(), param.getRefundImages());
        return switch (result) {
            case RConstants.ORDER_NOT_EXIST -> R.fail("订单不存在！");
            case RConstants.ORDER_ALREADY_REFUND -> R.fail("订单已经申请退款！");
            case RConstants.ERROR -> R.fail("申请失败！");
            default -> R.data(result);
        };
    }

    @Operation(summary = "【订单】退单取消", description = "退单取消")
    @PostMapping("/refund/cancel")
    public R<String> cancelRefundOrder(@RequestBody @Validated OrderIdParam param) {
        String result = memberOrderService.cancelRefundOrder(StpUtil.getLoginIdAsLong(), Long.valueOf(param.getOrderId()));
        return switch (result) {
            case RConstants.ORDER_NOT_EXIST -> R.fail("订单不存在！");
            case RConstants.ORDER_NOT_REFUND -> R.fail("订单未退单！");
            default -> R.data(result);
        };
    }

    @Operation(summary = "【订单】催单", description = "催单")
    @PostMapping("/rush")
    public R<String> rushOrder(@RequestBody @Validated OrderIdParam param) {
        memberOrderService.rushOrder(StpUtil.getLoginIdAsLong(), Long.valueOf(param.getOrderId()));
        return R.status(true);
    }

    @Operation(summary = "【订单】收货", description = "收货")
    @PostMapping("/receive")
    public R<String> receiveOrder(@RequestBody @Validated OrderIdParam param) {
        memberOrderService.receiveOrder(StpUtil.getLoginIdAsLong(), Long.valueOf(param.getOrderId()));
        return R.status(true);
    }

    @Operation(summary = "【订单】删除订单", description = "删除订单")
    @DeleteMapping
    public R<String> deleteOrder(@RequestBody @Validated OrderIdParam param) {
        memberOrderService.deleteOrder(StpUtil.getLoginIdAsLong(), Long.valueOf(param.getOrderId()));
        return R.status(true);
    }

    @Operation(summary = "【订单】待评价商品", description = "待评价商品")
    @GetMapping("/evaluate/list")
    public R<List<MemberOrderItem>> evaluateList() {
        return R.data(memberOrderService.evaluateList(StpUtil.getLoginIdAsLong()));
    }

    @Operation(summary = "【订单】评价商品上传图片", description = "评价商品上传图片")
    @PostMapping("/evaluate/upload")
    public R<String> uploadEvaluateImage(MultipartFile file) {
        try {
            URL url = CosClient.uploadFile(file, FileFolderConstants.EVALUATE.concat(Objects.requireNonNull(file.getOriginalFilename())));
            return R.data(url.toString());
        } catch (IOException e) {
            log.error("UserOrderController:: upload error");
            return R.fail("上传失败");
        }
    }

    @Operation(summary = "【订单】评价商品", description = "评价商品")
    @PostMapping("/evaluate")
    public R<String> evaluate(@RequestBody @Validated EvaluateParam param) {
        memberOrderService.evaluateWares(StpUtil.getLoginIdAsLong(), param.getOrderItemId(), param.getRate(), param.getEvaluateImages(), param.getRemarks());
        return R.status(true);
    }

    @Operation(summary = "【订单】删除订单列表", description = "删除订单列表")
    @GetMapping("/deleted/list")
    public R<List<MemberOrder>> deleteList() {
        return R.data(memberOrderService.deleteList(StpUtil.getLoginIdAsLong()));
    }
}