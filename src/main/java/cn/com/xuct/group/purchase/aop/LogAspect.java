/**
 * Copyright (C), 2015-2023, 263企业通信
 * FileName: LogAspect
 * Author:   Derek Xu
 * Date:     2023/5/12 17:36
 * Description:
 * History:
 * <author>          <time>          <version>          <desc>
 * 作者姓名           修改时间           版本号              描述
 */
package cn.com.xuct.group.purchase.aop;

import cn.com.xuct.group.purchase.annotation.Log;
import cn.com.xuct.group.purchase.entity.LogInfo;
import cn.com.xuct.group.purchase.service.LogInfoService;
import cn.com.xuct.group.purchase.service.UserService;
import cn.com.xuct.group.purchase.utils.JsonUtils;
import cn.dev33.satoken.stp.StpUtil;
import cn.hutool.extra.servlet.JakartaServletUtil;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.AfterReturning;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestAttributes;
import org.springframework.web.context.request.RequestContextHolder;

import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

/**
 * 〈一句话功能简述〉<br>
 * 〈〉
 *
 * @author Derek Xu
 * @create 2023/5/12
 * @since 1.0.0
 */
@Aspect
@Component
@RequiredArgsConstructor
public class LogAspect {

    /**
     * 操作版本号
     * 项目启动时从命令行传入，例如：java -jar xxx.war --version=201902
     */
    @Value("${version}")
    private String version;
    /**
     * 统计请求的处理时间
     */
    ThreadLocal<Long> startTime = new ThreadLocal<>();

    private final LogInfoService logInfoService;

    private final UserService userService;

    /**
     * @methodName：logPoinCut
     * @description：设置操作日志切入点 记录操作日志 在注解的位置切入代码
     * @author：tanyp
     * @dateTime：2021/11/18 14:22
     * @Params： []
     * @Return： void
     * @editNote：
     */
    @Pointcut("@annotation(cn.com.xuct.group.purchase.annotation.Log)")
    public void logPointCut() {
    }


    @Before("logPointCut()")
    public void doBefore() {
        // 接收到请求，记录请求开始时间
        startTime.set(System.currentTimeMillis());
    }

    /**
     * @methodName：doAfterReturning
     * @description：正常返回通知，拦截用户操作日志，连接点正常执行完成后执行， 如果连接点抛出异常，则不会执行
     * @author：tanyp
     * @dateTime：2021/11/18 14:21
     * @Params： [joinPoint, keys]
     * @Return： void
     * @editNote：
     */
    @AfterReturning(value = "logPointCut()", returning = "keys")
    public void doAfterReturning(JoinPoint joinPoint, Object keys) {
        HttpServletRequest request = this.getRequest();
        if (request == null) {
            return;
        }
        LogInfo logInfo = LogInfo.builder().build();
        try {
            // 从切面织入点处通过反射机制获取织入点处的方法
            MethodSignature signature = (MethodSignature) joinPoint.getSignature();

            // 获取切入点所在的方法
            Method method = signature.getMethod();

            // 获取请求的类名
            String className = joinPoint.getTarget().getClass().getName();

            // 获取操作
            Log log = method.getAnnotation(Log.class);
            if (Objects.nonNull(log)) {
                logInfo.setModule(log.modul());
                logInfo.setType(log.type());
                logInfo.setMessage(log.desc());
            }
            logInfo.setMethod(className + "." + method.getName()); // 请求的方法名
            logInfo.setReqParam(JsonUtils.mapToJson(converMap(request.getParameterMap()))); // 请求参数
            logInfo.setResParam(JsonUtils.obj2json(keys)); // 返回结果
            logInfo.setUserId(StpUtil.getLoginIdAsLong()); // 请求用户ID
            logInfo.setUserName(userService.findById(StpUtil.getLoginIdAsLong()).getUsername()); // 请求用户名称
            logInfo.setIp(JakartaServletUtil.getClientIP(request)); // 请求IP
            logInfo.setUri(request.getRequestURI()); // 请求URI
            logInfo.setVersion(version); // 操作版本
            logInfo.setTakeUpTime(System.currentTimeMillis() - startTime.get()); // 耗时
            logInfoService.save(logInfo);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    /**
     * @methodName：converMap
     * @description：转换request 请求参数
     * @author：tanyp
     * @dateTime：2021/11/18 14:12
     * @Params： [paramMap]
     * @Return： java.util.Map<java.lang.String, java.lang.String>
     * @editNote：
     */
    public Map<String, String> converMap(Map<String, String[]> paramMap) {
        Map<String, String> rtnMap = new HashMap<String, String>();
        for (String key : paramMap.keySet()) {
            rtnMap.put(key, paramMap.get(key)[0]);
        }
        return rtnMap;
    }


    private HttpServletRequest getRequest() {
        // 获取RequestAttributes
        RequestAttributes requestAttributes = RequestContextHolder.getRequestAttributes();
        if (requestAttributes == null) {
            return null;
        }
        // 从获取RequestAttributes中获取HttpServletRequest的信息
        return (HttpServletRequest) requestAttributes.resolveReference(RequestAttributes.REFERENCE_REQUEST);
    }
}