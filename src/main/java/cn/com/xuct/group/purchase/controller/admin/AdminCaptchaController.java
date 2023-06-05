/**
 * Copyright (C), 2015-2023, 263企业通信
 * FileName: CaptchaController
 * Author:   Derek Xu
 * Date:     2023/6/5 17:39
 * Description:
 * History:
 * <author>          <time>          <version>          <desc>
 * 作者姓名           修改时间           版本号              描述
 */
package cn.com.xuct.group.purchase.controller.admin;

import cn.com.xuct.group.purchase.base.res.R;
import cn.com.xuct.group.purchase.constants.RedisCacheConstants;
import cn.dev33.satoken.annotation.SaIgnore;
import cn.hutool.captcha.CaptchaUtil;
import cn.hutool.captcha.CircleCaptcha;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;
import java.util.concurrent.TimeUnit;

/**
 * 〈一句话功能简述〉<br>
 * 〈〉
 *
 * @author Derek Xu
 * @create 2023/6/5
 * @since 1.0.0
 */
@Tag(name = "【管理员-登录模块】")
@RequestMapping("/api/admin/v1/captcha")
@RequiredArgsConstructor
@RestController
public class AdminCaptchaController {

    private final StringRedisTemplate redisTemplate;

    @SaIgnore
    @SneakyThrows
    @GetMapping("")
    public void getCode(@RequestParam("randomStr") String randomStr, HttpServletResponse response) throws IOException {
        // 利用 hutool 工具，生成验证码图片资源
        CircleCaptcha captcha = CaptchaUtil.createCircleCaptcha(100, 40, 4, 5);
        // 获得生成的验证码字符
        String code = captcha.getCode();
        redisTemplate.opsForValue().set(RedisCacheConstants.ADMIN_LOGIN_CAPTCHA_KEY.concat(randomStr), code, 60 * 2, TimeUnit.SECONDS);
        // 将验证码图片的二进制数据写入【响应体 response 】
        captcha.write(response.getOutputStream());
    }
}