/**
 * Copyright (C), 2015-2021, XXX有限公司
 * FileName: GlobalExceptionHandler
 * Author:   Derek Xu
 * Date:     2021/11/17 17:07
 * Description:
 * History:
 * <author>          <time>          <version>          <desc>
 * 作者姓名           修改时间           版本号              描述
 */
package cn.com.xuct.group.purchase.handler;

import cn.com.xuct.group.purchase.base.res.R;
import cn.com.xuct.group.purchase.base.res.SvrResCode;
import cn.com.xuct.group.purchase.exception.SvrException;
import cn.dev33.satoken.exception.DisableServiceException;
import cn.dev33.satoken.exception.NotLoginException;
import cn.dev33.satoken.exception.NotPermissionException;
import lombok.extern.slf4j.Slf4j;
import me.chanjar.weixin.common.error.WxErrorException;
import org.springframework.http.HttpStatus;
import org.springframework.web.HttpRequestMethodNotSupportedException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;

/**
 * 〈一句话功能简述〉<br>
 * 〈〉
 *
 * @author Derek Xu
 * @create 2021/11/17
 * @since 1.0.0
 */
@Slf4j
@ControllerAdvice
public class GlobalExceptionHandler {

    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    @ExceptionHandler(value = WxErrorException.class)
    @ResponseBody
    public R<String> wxErrorExceptionHandler(WxErrorException e) {
        log.error("微信访问异常！原因是：{}", e.getMessage());
        return R.fail(e.getError().getErrorCode(), e.getMessage());
    }

    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ResponseBody
    @ExceptionHandler(value = HttpRequestMethodNotSupportedException.class)
    public R<String> methodNotSupportHandler(Exception e) {
        log.error("方法不匹配异常！原因是：{}", e.getMessage());
        return R.fail(SvrResCode.BASIC_METHOD_NOT_SUPPORT_ERROR);
    }

    @ResponseStatus(HttpStatus.UNAUTHORIZED)
    @ResponseBody
    @ExceptionHandler(value = NotLoginException.class)
    public R<String> notLoginException(NotLoginException e) {
        log.error("未登录！原因是：{}", e.getMessage());
        return R.success("未登录");
    }

    @ResponseStatus(HttpStatus.FAILED_DEPENDENCY)
    @ResponseBody
    @ExceptionHandler(value = DisableServiceException.class)
    public R<String> notLoginException(DisableServiceException e) {
        log.error("账号被禁用！原因是：{}", e.getMessage());
        return R.fail("账号被禁用");
    }

    @ResponseStatus(HttpStatus.FORBIDDEN)
    @ResponseBody
    @ExceptionHandler(value = NotPermissionException.class)
    public R<String> notLoginException(NotPermissionException e) {
        log.error("权限异常！原因是：{}", e.getMessage());
        return R.fail("暂时没有权限");
    }

    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    @ResponseBody
    @ExceptionHandler(value = SvrException.class)
    public R<String> svrExceptionHandler(SvrException e) {
        log.error("数据异常！原因是：{}", e.getMessage());
        return R.fail(e.getCode(), e.getMessage());
    }

    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    @ResponseBody
    @ExceptionHandler(value = Exception.class)
    public R<String> errorHandler(Exception e) {
        log.error("服务器异常！原因是：{}", e.getMessage());
        return R.fail(SvrResCode.BASIC_SERVER_ERROR);
    }
}