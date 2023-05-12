/**
 * Copyright (C), 2015-2023, 楚恬商场
 * FileName: UserCommentController
 * Author:   Derek Xu
 * Date:     2023/4/22 21:38
 * Description:
 * History:
 * <author>          <time>          <version>          <desc>
 * Derek Xu           修改时间           版本号              描述
 */
package cn.com.xuct.group.purchase.controller.app;

import cn.com.xuct.group.purchase.base.res.R;
import cn.com.xuct.group.purchase.base.vo.Column;
import cn.com.xuct.group.purchase.entity.User;
import cn.com.xuct.group.purchase.entity.MemberComment;
import cn.com.xuct.group.purchase.service.MemberCommentService;
import cn.com.xuct.group.purchase.service.MemberService;
import cn.dev33.satoken.stp.StpUtil;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.util.Assert;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * 〈一句话功能简述〉<br>
 * 〈〉
 *
 * @author Derek Xu
 * @create 2023/4/22
 * @since 1.0.0
 */
@Tag(name = "【留言模块】")
@RequestMapping("/api/v1/user/comment")
@RequiredArgsConstructor
@RestController
public class UserCommentController {

    private final MemberCommentService memberCommentService;

    private final MemberService memberService;

    @GetMapping("/list")
    @Operation(summary = "【留言】获取留言列表", description = "获取留言列表")
    public R<List<MemberComment>> list() {
        return R.data(memberCommentService.find(Column.of("user_id", StpUtil.getLoginIdAsLong())));
    }

    @PostMapping("")
    @Operation(summary = "【留言】添加留言", description = "添加留言")
    public R<String> add(@RequestBody @Validated MemberComment comment) {
        User user = memberService.findById(Long.valueOf(comment.getUser()));
        Assert.notNull(user, "用户未找到");
        memberCommentService.addComment(StpUtil.getLoginIdAsLong(), comment.getUser(), comment.getNickName(), comment.getAvatar(), comment.getContent());
        return R.status(true);
    }
}