/**
 * Copyright (C), 2015-2023, XXX有限公司
 * FileName: UserController
 * Author:   Derek Xu
 * Date:     2023/3/22 16:44
 * Description:
 * History:
 * <author>          <time>          <version>          <desc>
 * Derek Xu         修改时间           版本号              描述
 */
package cn.com.xuct.group.purchase.controller;

import cn.com.xuct.group.purchase.base.res.R;
import cn.com.xuct.group.purchase.client.imgurl.ImgUrlClient;
import cn.com.xuct.group.purchase.client.imgurl.ImgUrlData;
import cn.com.xuct.group.purchase.entity.User;
import cn.com.xuct.group.purchase.service.UserService;
import cn.com.xuct.group.purchase.utils.JsonUtils;
import cn.com.xuct.group.purchase.vo.param.UserParam;
import cn.dev33.satoken.stp.StpUtil;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.wildfly.common.Assert;

/**
 * 〈一句话功能简述〉<br>
 * 〈〉
 *
 * @author Derek Xu
 * @create 2023/3/22
 * @since 1.0.0
 */
@Tag(name = "【用户模块】")
@RequestMapping("/api/v1/user")
@RestController
@Slf4j
@RequiredArgsConstructor
public class UserController {


    private final UserService userService;

    private final ImgUrlClient imgUrlClient;

    @Operation(summary = "修改用户信息")
    @PutMapping
    public R<String> modify(@Validated @RequestBody UserParam param) {
        Long userId = StpUtil.getLoginIdAsLong();
        User user = userService.findById(userId);
        Assert.assertNotNull(user);
        userService.updateUserInfo(user, param.getPhone(), param.getNickname(), null);
        return R.status(true);
    }

    @Operation(summary = "上传头像")
    @PostMapping("/avatar/upload")
    public R<String> uploadAvatar(MultipartFile file) {
        ImgUrlData data = imgUrlClient.upload(file);
        if (data == null) {
            return R.fail("上传失败");
        }
        Long userId = StpUtil.getLoginIdAsLong();
        User user = userService.findById(userId);
        User updateUser = userService.updateUserInfo(user, null, null, data.getUrl());
        log.info("UserController:: user = {}", JsonUtils.obj2json(updateUser));
        return R.data(data.getUrl());
    }
}