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

import cn.binarywang.wx.miniapp.bean.WxMaPhoneNumberInfo;
import cn.com.xuct.group.purchase.base.res.R;
import cn.com.xuct.group.purchase.client.cos.client.CosClient;
import cn.com.xuct.group.purchase.config.WxMaConfiguration;
import cn.com.xuct.group.purchase.constants.FileFolderConstants;
import cn.com.xuct.group.purchase.entity.User;
import cn.com.xuct.group.purchase.service.UserService;
import cn.com.xuct.group.purchase.utils.JsonUtils;
import cn.com.xuct.group.purchase.vo.param.BindPhoneParam;
import cn.com.xuct.group.purchase.vo.param.UserParam;
import cn.dev33.satoken.stp.StpUtil;
import io.netty.util.internal.StringUtil;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import me.chanjar.weixin.common.error.WxErrorException;
import org.springframework.util.StringUtils;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.wildfly.common.Assert;

import java.io.IOException;
import java.net.URL;
import java.util.Objects;

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

    private final WxMaConfiguration wxMaConfiguration;

    @Operation(summary = "【用户】修改昵称手机号", description = "修改昵称手机号")
    @PutMapping
    public R<String> modify(@Validated @RequestBody UserParam param) {
        User user = userService.findById(StpUtil.getLoginIdAsLong());
        Assert.assertNotNull(user);
        userService.updateUserInfo(user, param.getPhone(), param.getNickname(), null);
        return R.status(true);
    }

    @Operation(summary = "【用户】绑定手机号", description = "上传头像")
    @PostMapping("/bind/phone")
    public R<String> bindPhone(@RequestBody @Validated BindPhoneParam param) {
        String phone = null;
        try {
            WxMaPhoneNumberInfo wxMaPhoneNumberInfo = wxMaConfiguration.getMaService().getUserService().getNewPhoneNoInfo(param.getCode());
            phone = wxMaPhoneNumberInfo.getPhoneNumber();
        } catch (WxErrorException e) {
            throw new RuntimeException(e);
        }
        if (!StringUtils.hasLength(phone)) {
            return R.fail("获取手机号失败");
        }
        User user = userService.findById(StpUtil.getLoginIdAsLong());
        Assert.assertNotNull(user);
        userService.updateUserInfo(user, phone, null, null);
        return R.data(phone);
    }

    @Operation(summary = "【用户】上传头像", description = "上传头像")
    @PostMapping("/avatar/upload")
    public R<String> uploadAvatar(MultipartFile file) {
        URL url = null;
        try {
            url = CosClient.uploadFile(file, FileFolderConstants.AVATAR.concat(Objects.requireNonNull(file.getOriginalFilename())));
        } catch (IOException e) {
            log.error("UserController:: update avatar error");
            return R.fail("上传失败");
        }
        Long userId = StpUtil.getLoginIdAsLong();
        User user = userService.findById(userId);
        User updateUser = userService.updateUserInfo(user, null, null, url.toString());
        log.info("UserController:: user = {}", JsonUtils.obj2json(updateUser));
        return R.data(url.toString());
    }
}