/**
 * Copyright (C), 2015-2023, 263企业通信
 * FileName: UserOpinionController
 * Author:   Derek Xu
 * Date:     2023/4/28 18:31
 * Description:
 * History:
 * <author>          <time>          <version>          <desc>
 * 作者姓名           修改时间           版本号              描述
 */
package cn.com.xuct.group.purchase.controller;

import cn.com.xuct.group.purchase.base.res.R;
import cn.com.xuct.group.purchase.entity.UserOpinion;
import cn.com.xuct.group.purchase.service.UserOpinionService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
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
 * @create 2023/4/28
 * @since 1.0.0
 */
@Tag(name = "【意见反馈模块】")
@RequestMapping("/api/v1/opinion")
@RestController
@Slf4j
@RequiredArgsConstructor
public class UserOpinionController {

    private final UserOpinionService userOpinionService;

    @Operation(summary = "【意见反馈】新增意见反馈", description = "新增意见反馈")
    @PostMapping()
    public R<String> add(@RequestBody @Validated UserOpinion opinion) {
        userOpinionService.save(opinion);
        return R.status(true);
    }




}