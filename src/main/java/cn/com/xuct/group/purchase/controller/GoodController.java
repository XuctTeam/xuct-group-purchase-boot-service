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
import cn.com.xuct.group.purchase.entity.Good;
import cn.com.xuct.group.purchase.service.GoodBrowseService;
import cn.com.xuct.group.purchase.service.GoodService;
import cn.com.xuct.group.purchase.service.UserGoodCartService;
import cn.com.xuct.group.purchase.service.UserGoodCollectService;
import cn.com.xuct.group.purchase.vo.param.AddCartParam;
import cn.com.xuct.group.purchase.vo.param.GoodCollectParam;
import cn.dev33.satoken.annotation.SaIgnore;
import cn.dev33.satoken.stp.StpUtil;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
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
    @Operation(summary = "【商品】列表", description = "查询商品列表")
    @GetMapping("/list")
    public R<List<Good>> list() {
        return R.data(goodService.findList());
    }

    @SaIgnore
    @Operation(summary = "【商品】详情", description = "查询商品详情")
    @GetMapping
    public R<Good> get(@RequestParam("id") Long id) {
        return R.data(goodService.getGood(id, StpUtil.isLogin() ? StpUtil.getLoginIdAsLong() : null));
    }

    @Operation(summary = "【商品】收藏或取消收藏", description = "收藏或取消收藏")
    @PostMapping("/collect")
    public R<String> collect(@RequestBody @Validated GoodCollectParam param) {
        userGoodCollectService.collect(StpUtil.getLoginIdAsLong(), param.getGid());
        return R.status(true);
    }

    @Operation(summary = "【商品】观看次数", description = "自增商品观看次数")
    public R<String> addGoodBrowse() {
        return null;
    }

    @Operation(summary = "【商品】添加购物车", description = "添加购物车")
    @PostMapping("/addcart")
    public R<String> addCart(@RequestBody @Validated AddCartParam addCartParam) {
        userGoodCartService.addCart(addCartParam.getGid(), StpUtil.getLoginIdAsLong());
        return R.status(true);
    }
}