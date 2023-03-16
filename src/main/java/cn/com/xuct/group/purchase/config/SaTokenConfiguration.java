/**
 * Copyright (C), 2015-2023, XXX有限公司
 * FileName: SaTokenConfigure
 * Author:   Derek Xu
 * Date:     2023/3/16 13:17
 * Description:
 * History:
 * <author>          <time>          <version>          <desc>
 * Derek Xu         修改时间           版本号              描述
 */
package cn.com.xuct.group.purchase.config;

import cn.dev33.satoken.interceptor.SaInterceptor;
import cn.dev33.satoken.stp.StpUtil;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

/**
 * 〈一句话功能简述〉<br> 
 * 〈〉
 *
 * @author Derek Xu
 * @create 2023/3/16
 * @since 1.0.0
 */
@Configuration
public class SaTokenConfiguration implements WebMvcConfigurer {

    // 注册拦截器
    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        // 注册 Sa-Token 拦截器，校验规则为 StpUtil.checkLogin() 登录校验。
        registry.addInterceptor(new SaInterceptor(handle -> StpUtil.checkLogin()))
                .addPathPatterns("/**")
                .excludePathPatterns("/login");
    }
}