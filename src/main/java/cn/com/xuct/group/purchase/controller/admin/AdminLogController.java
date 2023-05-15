/**
 * Copyright (C), 2015-2023, 263企业通信
 * FileName: AdminLogController
 * Author:   Derek Xu
 * Date:     2023/5/15 11:22
 * Description:
 * History:
 * <author>          <time>          <version>          <desc>
 * 作者姓名           修改时间           版本号              描述
 */
package cn.com.xuct.group.purchase.controller.admin;

import cn.com.xuct.group.purchase.base.res.R;
import cn.com.xuct.group.purchase.base.vo.PageData;
import cn.com.xuct.group.purchase.entity.LogInfo;
import cn.com.xuct.group.purchase.service.LogInfoService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.Parameters;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * 〈一句话功能简述〉<br>
 * 〈〉
 *
 * @author Derek Xu
 * @create 2023/5/15
 * @since 1.0.0
 */
@Tag(name = "【管理员-日志模块】")
@RequestMapping("/api/admin/v1/logs")
@RequiredArgsConstructor
@RestController
public class AdminLogController {

    private final LogInfoService logInfoService;

    @Operation(summary = "【日志】日志列表", description = "日志列表")
    @GetMapping("")
    @Parameters(value = {
            @Parameter(name = "pageNum", description = "当前页", required = true),
            @Parameter(name = "pageSize", description = "每页条数", required = true),
            @Parameter(name = "module", description = "操作模块"),
            @Parameter(name = "createTime", description = "操作时间")
    })
    public R<PageData<LogInfo>> pages(
            @RequestParam(value = "module", required = false) String module,
            @RequestParam("createTime[]") List<String> createTime,
            @RequestParam("pageNum") Integer pageNum, @RequestParam("pageSize") Integer pageSize) {
        return R.data(logInfoService.pagesList(module, createTime, pageNum, pageSize));
    }
}