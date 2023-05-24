/**
 * Copyright (C), 2015-2023, 263企业通信
 * FileName: AdminAppConfigController
 * Author:   Derek Xu
 * Date:     2023/5/24 16:29
 * Description:
 * History:
 * <author>          <time>          <version>          <desc>
 * 作者姓名           修改时间           版本号              描述
 */
package cn.com.xuct.group.purchase.controller.admin;

import cn.com.xuct.group.purchase.annotation.Log;
import cn.com.xuct.group.purchase.base.res.R;
import cn.com.xuct.group.purchase.constants.OptConstants;
import cn.com.xuct.group.purchase.entity.AppConfig;
import cn.com.xuct.group.purchase.service.AppConfigService;
import cn.dev33.satoken.annotation.SaCheckRole;
import cn.dev33.satoken.annotation.SaMode;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * 〈一句话功能简述〉<br>
 * 〈〉
 *
 * @author Derek Xu
 * @create 2023/5/24
 * @since 1.0.0
 */
@Tag(name = "【管理员-系统参数】")
@RequestMapping("/api/admin/v1/app/config")
@RequiredArgsConstructor
@RestController
@SaCheckRole(value = {"super_admin", "admin"}, mode = SaMode.OR)
public class AdminAppConfigController {

    private final AppConfigService appConfigService;

    @Operation(summary = "【系统参数】系统参数列表", description = "系统参数列表")
    @GetMapping("")
    public R<List<AppConfig>> list() {
        return R.data(appConfigService.list());
    }

    @Operation(summary = "【系统参数】编辑参数", description = "编辑参数")
    @Log(modul = "【系统参数】编辑参数", type = OptConstants.UPDATE, desc = "编辑参数")
    @PutMapping("")
    public R<String> update(@RequestBody @Validated AppConfig appConfig) {
        appConfigService.saveAppConfig(appConfig);
        return R.status(true);
    }
}