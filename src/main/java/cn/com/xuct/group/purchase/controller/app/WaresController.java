/**
 * Copyright (C), 2015-2023, XXX有限公司
 * FileName: WaresController
 * Author:   Derek Xu
 * Date:     2023/3/27 11:27
 * Description:
 * History:
 * <author>          <time>          <version>          <desc>
 * Derek Xu         修改时间           版本号              描述
 */
package cn.com.xuct.group.purchase.controller.app;

import cn.com.xuct.group.purchase.base.res.R;
import cn.com.xuct.group.purchase.base.vo.Column;
import cn.com.xuct.group.purchase.entity.Wares;
import cn.com.xuct.group.purchase.service.*;
import cn.com.xuct.group.purchase.vo.param.AddCartParam;
import cn.com.xuct.group.purchase.vo.param.CartManyWaresParam;
import cn.com.xuct.group.purchase.vo.param.UpdateCartNumParam;
import cn.com.xuct.group.purchase.vo.param.WaresParam;
import cn.com.xuct.group.purchase.vo.result.CartResult;
import cn.com.xuct.group.purchase.vo.result.MemberWaresResult;
import cn.com.xuct.group.purchase.vo.result.WaresResult;
import cn.dev33.satoken.annotation.SaIgnore;
import cn.dev33.satoken.stp.StpUtil;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.Parameters;
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
@RequestMapping("/api/v1/wares")
@RestController
@Slf4j
@RequiredArgsConstructor
public class WaresController {

    private final WaresService waresService;
    private final MemberWaresCollectService memberWaresCollectService;
    private final MemberWaresCartService memberWaresCartService;
    private final WaresBrowseService waresBrowseService;
    private final MemberBrowseService memberBrowseService;

    @SaIgnore
    @GetMapping("/list")
    @Operation(summary = "【商品】列表", description = "查询商品列表")
    public R<List<Wares>> list() {
        return R.data(waresService.findList());
    }

    @SaIgnore
    @GetMapping
    @Operation(summary = "【商品】详情", description = "查询商品详情")
    @Parameters(value = {
            @Parameter(name = "id", description = "商品ID"),
    })
    public R<WaresResult> get(@RequestParam("id") Long id) {
        return R.data(waresService.getWareInfo(id, StpUtil.isLogin() ? StpUtil.getLoginIdAsLong() : null));
    }

    @SaIgnore
    @PostMapping("/browse")
    @Operation(summary = "【商品】观看次数", description = "自增商品观看次数")
    public R<String> addWaresBrowse(@RequestBody @Validated WaresParam param) {
        waresBrowseService.browse(StpUtil.isLogin() ? StpUtil.getLoginIdAsLong() : null, param.getWaresId());
        return R.status(true);
    }

    @PostMapping("/collect")
    @Operation(summary = "【商品】收藏或取消收藏", description = "收藏或取消收藏")
    public R<String> collect(@RequestBody @Validated WaresParam param) {
        memberWaresCollectService.collect(StpUtil.getLoginIdAsLong(), param.getWaresId());
        return R.status(true);
    }

    @PostMapping("/cart/add")
    @Operation(summary = "【购物车】添加购物车", description = "添加购物车")
    public R<String> addCart(@RequestBody @Validated AddCartParam addCartParam) {
        memberWaresCartService.addCart(addCartParam.getGid(), StpUtil.getLoginIdAsLong());
        return R.status(true);
    }

    @GetMapping("/cart/list")
    @Operation(summary = "【购物车】购物车列表", description = "购物车列表")
    public R<List<CartResult>> cartList() {
        return R.data(memberWaresCartService.cartList(StpUtil.getLoginIdAsLong(), Lists.newArrayList()));
    }

    @PostMapping("/cart/update/num")
    @Operation(summary = "【购物车】修改购物车数量", description = "修改购物车数量")
    public R<String> updateCartWaresNum(@RequestBody @Validated UpdateCartNumParam param) {
        memberWaresCartService.updateCartWaresNum(StpUtil.getLoginIdAsLong(), param.getWaresId(), param.getNum());
        return R.status(true);
    }

    @PostMapping("/cart/del")
    @Operation(summary = "【购物车】删除购物车商品", description = "删除购物车商品")
    public R<String> deleteCartWares(@RequestBody @Validated CartManyWaresParam param) {
        memberWaresCartService.deleteCartWares(param.getWaresIds(), StpUtil.getLoginIdAsLong());
        return R.status(true);
    }

    @DeleteMapping("/cart/del/all")
    @Operation(summary = "【购物车】清空购物车", description = "清空购物车")
    public R<String> cleanCart() {
        memberWaresCartService.delete(Column.of("member_id", StpUtil.getLoginIdAsLong()));
        return R.status(true);
    }

    @Operation(summary = "【用户】我的收藏", description = "我的收藏")
    @GetMapping("/user/collect")
    public R<List<MemberWaresResult>> findUserCollect() {
        return R.data(memberWaresCollectService.list(StpUtil.getLoginIdAsLong()));
    }

    @Operation(summary = "【用户】我的浏览", description = "我的浏览")
    @GetMapping("/user/browse")
    public R<List<Wares>> findUserBrowse() {
        return R.data(memberBrowseService.list(StpUtil.getLoginIdAsLong()));
    }

    @Operation(summary = "【用户】删除浏览记录", description = "删除浏览记录")
    @Parameters(value = {
            @Parameter(name = "waresId", description = "商品ID"),
    })
    @DeleteMapping("/user/browse/{waresId}")
    public R<String> deleteUserBrowse(@PathVariable("waresId") Long waresId) {
        memberBrowseService.delete(StpUtil.getLoginIdAsLong(), waresId);
        return R.status(true);
    }
}