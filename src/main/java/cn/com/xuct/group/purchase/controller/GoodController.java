/**
 * Copyright (C), 2015-2023, XXX有限公司
 * FileName: GoodController
 * Author:   Derek Xu
 * Date:     2023/3/27 11:27
 * Description:
 * History:
 * <author>          <time>          <version>          <desc>
 * Derek Xu         修改时间           版本号              描述
 */
package cn.com.xuct.group.purchase.controller;

import cn.com.xuct.group.purchase.base.res.R;
import cn.com.xuct.group.purchase.base.vo.Column;
import cn.com.xuct.group.purchase.entity.Good;
import cn.com.xuct.group.purchase.service.GoodBrowseService;
import cn.com.xuct.group.purchase.service.GoodService;
import cn.com.xuct.group.purchase.service.UserGoodCartService;
import cn.com.xuct.group.purchase.service.UserGoodCollectService;
import cn.com.xuct.group.purchase.vo.param.AddCartParam;
import cn.com.xuct.group.purchase.vo.param.CartManyGoodParam;
import cn.com.xuct.group.purchase.vo.param.GoodParam;
import cn.com.xuct.group.purchase.vo.param.UpdateCartNumParam;
import cn.com.xuct.group.purchase.vo.result.CartResult;
import cn.dev33.satoken.annotation.SaIgnore;
import cn.dev33.satoken.stp.StpUtil;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.assertj.core.util.Lists;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * 〈一句话功能简述〉<br>
 * 〈〉
 *
 * @author Derek Xu
 * @create 2023/3/27
 * @since 1.0.0
 */
@Tag(name = "【商品模块】")
@RequestMapping("/api/v1/good")
@RestController
@Slf4j
@RequiredArgsConstructor
public class GoodController {

    private final GoodService goodService;
    private final UserGoodCollectService userGoodCollectService;
    private final UserGoodCartService userGoodCartService;
    private final GoodBrowseService goodBrowseService;

    @SaIgnore
    @GetMapping("/list")
    @Operation(summary = "【商品】列表", description = "查询商品列表")
    public R<List<Good>> list() {
        return R.data(goodService.findList());
    }

    @SaIgnore
    @GetMapping
    @Operation(summary = "【商品】详情", description = "查询商品详情")
    public R<Good> get(@RequestParam("id") Long id) {
        return R.data(goodService.getGood(id, StpUtil.isLogin() ? StpUtil.getLoginIdAsLong() : null));
    }

    @SaIgnore
    @PostMapping("/browse")
    @Operation(summary = "【商品】观看次数", description = "自增商品观看次数")
    public R<String> addGoodBrowse(@RequestBody @Validated GoodParam param) {
        goodBrowseService.browse(param.getGid());
        return R.status(true);
    }

    @PostMapping("/collect")
    @Operation(summary = "【商品】收藏或取消收藏", description = "收藏或取消收藏")
    public R<String> collect(@RequestBody @Validated GoodParam param) {
        userGoodCollectService.collect(StpUtil.getLoginIdAsLong(), param.getGid());
        return R.status(true);
    }

    @PostMapping("/cart/add")
    @Operation(summary = "【购物车】添加购物车", description = "添加购物车")
    public R<String> addCart(@RequestBody @Validated AddCartParam addCartParam) {
        userGoodCartService.addCart(addCartParam.getGid(), StpUtil.getLoginIdAsLong());
        return R.status(true);
    }

    @GetMapping("/cart/list")
    @Operation(summary = "【购物车】购物车列表", description = "购物车列表")
    public R<List<CartResult>> cartList() {
        return R.data(userGoodCartService.cartList(StpUtil.getLoginIdAsLong(), Lists.newArrayList()));
    }

    @PostMapping("/cart/order/list")
    @Operation(summary = "【购物车】确认购物车列表", description = "确认购物车列表")
    public R<List<CartResult>> cartOrderList(@RequestBody @Validated CartManyGoodParam param) {
        return R.data(userGoodCartService.cartList(StpUtil.getLoginIdAsLong(), param.getGids()));
    }

    @PostMapping("/cart/update/num")
    @Operation(summary = "【购物车】修改购物车数量", description = "修改购物车数量")
    public R<String> updateCartGoodNum(@RequestBody @Validated UpdateCartNumParam param) {
        userGoodCartService.updateCartGoodNum(StpUtil.getLoginIdAsLong(), param.getGid(), param.getNum());
        return R.status(true);
    }

    @PostMapping("/cart/del")
    @Operation(summary = "【购物车】删除购物车商品", description = "删除购物车商品")
    public R<String> deleteCartGood(@RequestBody @Validated CartManyGoodParam param) {
        userGoodCartService.deleteCartGood(param.getGids(), StpUtil.getLoginIdAsLong());
        return R.status(true);
    }

    @DeleteMapping("/cart/del/all")
    @Operation(summary = "【购物车】清空购物车", description = "清空购物车")
    public R<String> cleanCart() {
        userGoodCartService.delete(Column.of("user_id", StpUtil.getLoginIdAsLong()));
        return R.status(true);
    }


}