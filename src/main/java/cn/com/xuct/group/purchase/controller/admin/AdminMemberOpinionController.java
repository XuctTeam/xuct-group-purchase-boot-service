/**
 * Copyright (C), 2015-2023, 263企业通信
 * FileName: AdminMemberOpinionController
 * Author:   Derek Xu
 * Date:     2023/5/25 18:05
 * Description:
 * History:
 * <author>          <time>          <version>          <desc>
 * 作者姓名           修改时间           版本号              描述
 */
package cn.com.xuct.group.purchase.controller.admin;

import cn.com.xuct.group.purchase.annotation.Log;
import cn.com.xuct.group.purchase.base.res.R;
import cn.com.xuct.group.purchase.base.vo.PageData;
import cn.com.xuct.group.purchase.constants.OptConstants;
import cn.com.xuct.group.purchase.entity.MemberOpinion;
import cn.com.xuct.group.purchase.service.MemberOpinionService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.Parameters;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

/**
 * 〈一句话功能简述〉<br>
 * 〈〉
 *
 * @author Derek Xu
 * @create 2023/5/25
 * @since 1.0.0
 */
@Tag(name = "【管理员-意见反馈模块】")
@RequestMapping("/api/admin/v1/opinion")
@RequiredArgsConstructor
@RestController
public class AdminMemberOpinionController {

    private final MemberOpinionService memberOpinionService;

    @Operation(summary = "【意见反馈】意见反馈列表", description = "意见反馈列表")
    @Parameters(value = {
            @Parameter(name = "pageNum", description = "当前页", required = true),
            @Parameter(name = "pageSize", description = "每页条数", required = true),
            @Parameter(name = "nickname", description = "昵称"),
            @Parameter(name = "status", description = "状态")
    })
    @GetMapping("")
    public R<PageData<MemberOpinion>> pageList(@RequestParam(value = "nickname", required = false) String nickname,
                                               @RequestParam(value = "status", required = false) Integer status,
                                               @RequestParam("pageNum") Integer pageNum, @RequestParam("pageSize") Integer pageSize) {
        return R.data(memberOpinionService.findPageList(nickname, status, pageNum, pageSize));
    }


    @Operation(summary = "【意见反馈】意见反馈", description = "意见反馈")
    @Log(modul = "【意见反馈】意见反馈", type = OptConstants.UPDATE, desc = "意见反馈")
    @PutMapping("")
    public R<String> saveFeedback(@RequestBody @Validated MemberOpinion opinion){
        memberOpinionService.saveFeedback(opinion);
        return R.status(true);
    }
}