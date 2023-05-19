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
package cn.com.xuct.group.purchase.controller.app;

import cn.binarywang.wx.miniapp.bean.WxMaJscode2SessionResult;
import cn.com.xuct.group.purchase.base.res.R;
import cn.com.xuct.group.purchase.config.WxMaConfiguration;
import cn.com.xuct.group.purchase.entity.Member;
import cn.com.xuct.group.purchase.mapstruct.ILoginResultConvert;
import cn.com.xuct.group.purchase.service.MemberService;
import cn.com.xuct.group.purchase.vo.param.WxCodeParam;
import cn.com.xuct.group.purchase.vo.result.LoginResult;
import cn.dev33.satoken.annotation.SaIgnore;
import cn.dev33.satoken.stp.SaTokenInfo;
import cn.dev33.satoken.stp.StpUtil;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import me.chanjar.weixin.common.error.WxErrorException;
import org.springframework.util.StringUtils;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

/**
 * 〈一句话功能简述〉<br>
 * 〈〉
 *
 * @author Derek Xu
 * @create 2023/3/16
 * @since 1.0.0
 */
@Tag(name = "【登录模块】")
@RequestMapping("/api/v1/login")
@RequiredArgsConstructor
@RestController
public class LoginController {

    private final WxMaConfiguration wxMaConfiguration;

    private final MemberService memberService;

    @SaIgnore
    @Operation(summary = "【登录】获取微信Session", description = "根据code获取小程序SessionInfo")
    @PostMapping
    public R<LoginResult> login(@Validated @RequestBody WxCodeParam wxCodeParam) throws WxErrorException {
        WxMaJscode2SessionResult session = wxMaConfiguration.getMaService().jsCode2SessionInfo(wxCodeParam.getCode());
        if (session == null || !StringUtils.hasLength(session.getOpenid())) {
            return R.fail("查询session失败");
        }
        Member member = memberService.findByOpenId(session.getOpenid());
        // 第1步，先登录上
        StpUtil.login(member.getId());
        return R.data(ILoginResultConvert.INSTANCE.memberToken2LoginResult(member, StpUtil.getTokenInfo()));

    }

    @SaIgnore
    @Operation(summary = "【登录】检查Token有效期", description = "检查Token有效期")
    @PostMapping("/token/check")
    public R<Boolean> checkToken() {
        SaTokenInfo saResult = StpUtil.getTokenInfo();
        if (!saResult.getIsLogin()) {
            return R.data(false);
        }
        return R.data(true);
    }

    @Operation(summary = "【登录】退出", description = "退出")
    @DeleteMapping
    public R<String> logout() {
        StpUtil.logout(StpUtil.getLoginIdAsLong());
        return R.status(true);
    }
}