/**
 * Copyright (C), 2015-2023, 263企业通信
 * FileName: LoginController
 * Author:   Derek Xu
 * Date:     2023/5/8 16:16
 * Description:
 * History:
 * <author>          <time>          <version>          <desc>
 * 作者姓名           修改时间           版本号              描述
 */
package cn.com.xuct.group.purchase.controller.admin;

import cn.com.xuct.group.purchase.annotation.Log;
import cn.com.xuct.group.purchase.base.res.R;
import cn.com.xuct.group.purchase.constants.OptConstants;
import cn.com.xuct.group.purchase.entity.User;
import cn.com.xuct.group.purchase.mapstruct.ILoginResultConvert;
import cn.com.xuct.group.purchase.service.UserService;
import cn.com.xuct.group.purchase.vo.param.admin.AdminLoginParam;
import cn.com.xuct.group.purchase.vo.result.LoginResult;
import cn.dev33.satoken.annotation.SaIgnore;
import cn.dev33.satoken.stp.StpUtil;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * 〈一句话功能简述〉<br>
 * 〈〉
 *
 * @author Derek Xu
 * @create 2023/5/8
 * @since 1.0.0
 */
@Tag(name = "【管理员-登录模块】")
@RequestMapping("/api/admin/v1/login")
@RequiredArgsConstructor
@RestController
public class AdminLoginController {

    private final UserService userService;

    @SaIgnore
    @PostMapping("")
    @Operation(summary = "【登录】后台管理登录", description = "后台管理登录")
    @Log(modul = "【登录】后台管理登录", type = OptConstants.SELECT, desc = "后台管理登录")
    public R<LoginResult> login(@RequestBody @Validated AdminLoginParam param) {
        User user = userService.findByUsername(param.getUsername());
        if (user == null) {
            return R.fail("用户不存在！");
        }
        if (!user.getPassword().equals(param.getPassword())) {
            return R.fail("用户名或密码错误！");
        }
        // 校验指定账号是否已被封禁，如果被封禁则抛出异常 `DisableServiceException`
        StpUtil.checkDisable(user.getId());
        // 第1步，先登录上
        StpUtil.login(user.getId());
        return R.data(ILoginResultConvert.INSTANCE.userToken2LoginResult(user, StpUtil.getTokenInfo()));
    }


}