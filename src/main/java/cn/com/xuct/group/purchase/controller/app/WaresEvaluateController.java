/**
 * Copyright (C), 2015-2023, 263企业通信
 * FileName: WaresEvaluateController
 * Author:   Derek Xu
 * Date:     2023/6/7 17:59
 * Description:
 * History:
 * <author>          <time>          <version>          <desc>
 * 作者姓名           修改时间           版本号              描述
 */
package cn.com.xuct.group.purchase.controller.app;

import cn.com.xuct.group.purchase.base.res.R;
import cn.com.xuct.group.purchase.client.cos.client.CosClient;
import cn.com.xuct.group.purchase.constants.FileFolderConstants;
import cn.com.xuct.group.purchase.entity.MemberOrderItem;
import cn.com.xuct.group.purchase.entity.MemberWaresEvaluate;
import cn.com.xuct.group.purchase.service.MemberWaresEvaluateService;
import cn.com.xuct.group.purchase.vo.param.EvaluateParam;
import cn.dev33.satoken.annotation.SaIgnore;
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

/**
 * 〈一句话功能简述〉<br>
 * 〈〉
 *
 * @author Derek Xu
 * @create 2023/6/7
 * @since 1.0.0
 */
@Tag(name = "【商品评价模块】")
@RequestMapping("/api/v1/evaluate")
@RestController
@Slf4j
@RequiredArgsConstructor
public class WaresEvaluateController {

    private final MemberWaresEvaluateService memberWaresEvaluateService;

    @Operation(summary = "【商品评价】待评价商品", description = "待评价商品")
    @GetMapping("/list")
    public R<List<MemberOrderItem>> evaluateList() {
        return R.data(memberWaresEvaluateService.evaluateList(StpUtil.getLoginIdAsLong()));
    }

    @SaIgnore
    @Operation(summary = "【商品评价】", description = "商品评价列表")
    @GetMapping("/wares/list")
    @Parameters(value = {
            @Parameter(name = "waresId", description = "商品ID"),
    })
    public R<List<MemberWaresEvaluate>> evaluateWaresList(@RequestParam("waresId") Long waresId, @RequestParam("top") Integer top) {
        return R.data(memberWaresEvaluateService.evaluateWaresList(waresId, top));
    }

    @Operation(summary = "【商品评价】评价商品上传图片", description = "评价商品上传图片")
    @PostMapping("/upload")
    public R<String> uploadEvaluateImage(MultipartFile file) {
        try {
            URL url = CosClient.uploadFile(file, FileFolderConstants.EVALUATE.concat(Objects.requireNonNull(file.getOriginalFilename())));
            return R.data(url.toString());
        } catch (IOException e) {
            log.error("UserOrderController:: upload error");
            return R.fail("上传失败");
        }
    }

    @Operation(summary = "【商品评价】评价商品", description = "评价商品")
    @PostMapping("")
    public R<String> evaluate(@RequestBody @Validated EvaluateParam param) {
        memberWaresEvaluateService.evaluateWares(StpUtil.getLoginIdAsLong(), param.getOrderItemId(), param.getRate(), param.getEvaluateImages(), param.getRemarks());
        return R.status(true);
    }
}