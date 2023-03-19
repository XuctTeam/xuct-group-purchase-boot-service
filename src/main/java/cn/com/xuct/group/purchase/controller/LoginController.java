/**
 * Copyright (C), 2015-2023, XXX有限公司
 * FileName: LoginController
 * Author:   Derek Xu
 * Date:     2023/3/16 9:50
 * Description:
 * History:
 * <author>          <time>          <version>          <desc>
 * Derek Xu         修改时间           版本号              描述
 */
package cn.com.xuct.group.purchase.controller;

import cn.binarywang.wx.miniapp.bean.WxMaJscode2SessionResult;
import cn.com.xuct.group.purchase.base.res.R;
import cn.com.xuct.group.purchase.config.WxMaConfiguration;
import cn.com.xuct.group.purchase.entity.User;
import cn.com.xuct.group.purchase.service.UserService;
import cn.com.xuct.group.purchase.vo.param.WxCodeParam;
import cn.com.xuct.group.purchase.vo.result.LoginResult;
import cn.dev33.satoken.stp.SaTokenInfo;
import cn.dev33.satoken.stp.StpUtil;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import me.chanjar.weixin.common.error.WxErrorException;
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
 * @create 2023/3/16
 * @since 1.0.0
 */
@Tag(name = "【登录模块】")
@RequestMapping("/login")
@RequiredArgsConstructor
@RestController
public class LoginController {

    private final WxMaConfiguration wxMaConfiguration;

    private final UserService userService;

    @Operation(summary = "获取微信Session", description = "根据code获取小程序SessionInfo")
    @PostMapping("/code")
    public R<LoginResult> login(@Validated @RequestBody WxCodeParam wxCodeParam) throws WxErrorException {
        WxMaJscode2SessionResult session = wxMaConfiguration.getMaService().jsCode2SessionInfo(wxCodeParam.getCode());
        if(session == null){
            R.fail("查询session失败");
        }
        User user = userService.findByOpenId(session.getOpenid());
        // 第1步，先登录上
        StpUtil.login(user.getId());
        // 第2步，获取 Token  相关参数
        SaTokenInfo tokenInfo = StpUtil.getTokenInfo();
        return R.data(LoginResult.builder().tokenName(tokenInfo.getTokenName()).tokenValue(tokenInfo.getTokenValue()).openId(user.getOpenId()).build());
    }
}