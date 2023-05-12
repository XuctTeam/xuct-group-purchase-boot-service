/**
 * Copyright (C), 2015-2023, 263企业通信
 * FileName: UserOpinionController
 * Author:   Derek Xu
 * Date:     2023/4/28 18:31
 * Description:
 * History:
 * <author>          <time>          <version>          <desc>
 * 作者姓名           修改时间           版本号              描述
 */
package cn.com.xuct.group.purchase.controller.app;

import cn.com.xuct.group.purchase.base.res.R;
import cn.com.xuct.group.purchase.client.cos.client.CosClient;
import cn.com.xuct.group.purchase.constants.FileFolderConstants;
import cn.com.xuct.group.purchase.entity.MemberOpinion;
import cn.com.xuct.group.purchase.service.MemberOpinionService;
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
 * @create 2023/4/28
 * @since 1.0.0
 */
@Tag(name = "【意见反馈模块】")
@RequestMapping("/api/v1/opinion")
@RestController
@Slf4j
@RequiredArgsConstructor
public class UserOpinionController {

    private final MemberOpinionService memberOpinionService;

    @Operation(summary = "【意见反馈】新增意见反馈", description = "新增意见反馈")
    @PostMapping()
    public R<String> add(@RequestBody @Validated MemberOpinion opinion) {
        opinion.setUserId(StpUtil.getLoginIdAsLong());
        opinion.setStatus(false);
        memberOpinionService.save(opinion);
        return R.status(true);
    }

    @Operation(summary = "【意见反馈】反馈列表", description = "反馈列表")
    @GetMapping("/list")
    public R<List<MemberOpinion>> list() {
        return R.data(memberOpinionService.list(StpUtil.getLoginIdAsLong()));
    }

    @Operation(summary = "【意见反馈】上传反馈图片", description = "上传反馈图片")
    @PostMapping("/upload")
    public R<String> uploadAvatar(MultipartFile file) {
        URL url = null;
        try {
            url = CosClient.uploadFile(file, FileFolderConstants.OPINION.concat(Objects.requireNonNull(file.getOriginalFilename())));
        } catch (IOException e) {
            log.error("UserController:: update avatar error");
            return R.fail("上传失败");
        }
        return R.data(url.toString());
    }

    @Operation(summary = "【意见反馈】获取详情", description = "获取详情")
    @GetMapping("")
    @Parameters(value = {
            @Parameter(name = "id", description = "详情ID"),
    })
    public R<MemberOpinion> get(@RequestParam("id") String id) {
        return R.data(memberOpinionService.getById(Long.valueOf(id)));
    }
}