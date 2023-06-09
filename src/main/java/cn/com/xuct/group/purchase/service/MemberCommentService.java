/**
 * Copyright (C), 2015-2023, 楚恬商场
 * FileName: UserCommentService
 * Author:   Derek Xu
 * Date:     2023/4/22 18:32
 * Description:
 * History:
 * <author>          <time>          <version>          <desc>
 * Derek Xu           修改时间           版本号              描述
 */
package cn.com.xuct.group.purchase.service;

import cn.com.xuct.group.purchase.base.service.IBaseService;
import cn.com.xuct.group.purchase.entity.MemberComment;
import cn.com.xuct.group.purchase.mapper.MemberCommentMapper;

/**
 * 〈一句话功能简述〉<br>
 * 〈〉
 *
 * @author Derek Xu
 * @create 2023/4/22
 * @since 1.0.0
 */
public interface MemberCommentService extends IBaseService<MemberCommentMapper, MemberComment> {

    /**
     * 添加留言
     *
     * @param memberId
     * @param user
     * @param nickName
     * @param avatar
     * @param content
     */
    void addComment(final Long memberId, final String user, final String nickName , final String avatar, final String content);
}