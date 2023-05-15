/**
 * Copyright (C), 2015-2023, 263企业通信
 * FileName: AdminUploadController
 * Author:   Derek Xu
 * Date:     2023/5/15 9:36
 * Description:
 * History:
 * <author>          <time>          <version>          <desc>
 * 作者姓名           修改时间           版本号              描述
 */
package cn.com.xuct.group.purchase.controller.admin;

import cn.com.xuct.group.purchase.base.res.R;
import cn.com.xuct.group.purchase.client.cos.client.CosClient;
import cn.com.xuct.group.purchase.constants.FileFolderConstants;
import cn.dev33.satoken.annotation.SaCheckRole;
import cn.dev33.satoken.annotation.SaMode;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.net.URL;
import java.util.Objects;

/**
 * 〈一句话功能简述〉<br>
 * 〈〉
 *
 * @author Derek Xu
 * @create 2023/5/15
 * @since 1.0.0
 */
@Slf4j
@Tag(name = "【管理员-上传模块】")
@RequestMapping("/api/admin/v1/upload")
@RequiredArgsConstructor
@RestController
public class AdminUploadController {

    @SaCheckRole(value = {"super_admin", "admin"}, mode = SaMode.OR)
    @Operation(summary = "【文件上传】上传文件", description = "上传文件")
    @PostMapping("")
    public R<String> uploadAvatar(MultipartFile file) {
        URL url = null;
        try {
            url = CosClient.uploadFile(file, FileFolderConstants.ADMIN.concat(Objects.requireNonNull(file.getOriginalFilename())));
        } catch (IOException e) {
            log.error("UserController:: update avatar error");
            return R.fail("上传失败");
        }
        return R.data(url.toString());
    }
}