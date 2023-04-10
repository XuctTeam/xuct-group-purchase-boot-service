/**
 * Copyright (C), 2015-2023, 263企业通信
 * FileName: AppConfigController
 * Author:   Derek Xu
 * Date:     2023/4/10 11:21
 * Description:
 * History:
 * <author>          <time>          <version>          <desc>
 * 作者姓名           修改时间           版本号              描述
 */
package cn.com.xuct.group.purchase.controller;

import cn.com.xuct.group.purchase.base.res.R;
import cn.com.xuct.group.purchase.base.vo.Column;
import cn.com.xuct.group.purchase.entity.AppConfig;
import cn.com.xuct.group.purchase.service.AppConfigService;
import cn.dev33.satoken.annotation.SaIgnore;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

/**
 * 〈一句话功能简述〉<br> 
 * 〈〉
 *
 * @author Derek Xu
 * @create 2023/4/10
 * @since 1.0.0
 */
@Tag(name = "【配置模块】")
@RequestMapping("/api/v1/config")
@RestController
@Slf4j
@RequiredArgsConstructor
@SaIgnore
public class AppConfigController {

    private final AppConfigService appConfigService;

    @GetMapping
    @Operation(summary = "查询基础配置")
    public R<AppConfig> get(@RequestParam("type")Integer type){
         return R.data(appConfigService.get(Column.of("type" , type)));
    }
}