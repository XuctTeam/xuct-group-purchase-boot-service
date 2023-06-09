/**
 * Copyright (C), 2015-2023, 楚恬商场
 * FileName: UserCommentServiceImpl
 * Author:   Derek Xu
 * Date:     2023/4/22 18:32
 * Description:
 * History:
 * <author>          <time>          <version>          <desc>
 * Derek Xu           修改时间           版本号              描述
 */
package cn.com.xuct.group.purchase.service.impl;

import cn.com.xuct.group.purchase.base.service.BaseServiceImpl;
import cn.com.xuct.group.purchase.entity.MemberComment;
import cn.com.xuct.group.purchase.mapper.MemberCommentMapper;
import cn.com.xuct.group.purchase.service.MemberCommentService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

/**
 * 〈一句话功能简述〉<br>
 * 〈〉
 *
 * @author Derek Xu
 * @create 2023/4/22
 * @since 1.0.0
 */
@Slf4j
@Service
@RequiredArgsConstructor
public class MemberCommentServiceImpl extends BaseServiceImpl<MemberCommentMapper, MemberComment> implements MemberCommentService {

    @Override
    public void addComment(Long memberId, String user, final String nickName, String avatar, String content) {
        MemberComment comment = new MemberComment();
        comment.setUser(user);
        comment.setMemberId(memberId);
        comment.setAvatar(avatar);
        comment.setContent(content);
        comment.setNickName(nickName);
        this.save(comment);
    }
}