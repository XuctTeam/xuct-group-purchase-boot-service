/**
 * Copyright (C), 2015-2023, 263企业通信
 * FileName: UserOpinionService
 * Author:   Derek Xu
 * Date:     2023/4/28 18:29
 * Description:
 * History:
 * <author>          <time>          <version>          <desc>
 * 作者姓名           修改时间           版本号              描述
 */
package cn.com.xuct.group.purchase.service;

import cn.com.xuct.group.purchase.base.service.IBaseService;
import cn.com.xuct.group.purchase.base.vo.PageData;
import cn.com.xuct.group.purchase.entity.MemberOpinion;
import cn.com.xuct.group.purchase.mapper.MemberOpinionMapper;

import java.util.List;

/**
 * 〈一句话功能简述〉<br>
 * 〈〉
 *
 * @author Derek Xu
 * @create 2023/4/28
 * @since 1.0.0
 */
public interface MemberOpinionService extends IBaseService<MemberOpinionMapper, MemberOpinion> {

    /**
     * 【会员】反馈列表
     *
     * @param memberId
     * @return
     */
    List<MemberOpinion> list(final Long memberId);

    /**
     * 【管理员】分页查询反馈
     *
     * @param nickname
     * @param status
     * @param pageNum
     * @param pageSize
     * @return
     */
    PageData<MemberOpinion> findPageList(final String nickname, final Integer status, final Integer pageNum, final Integer pageSize);


    /**
     * 【管理员】反馈
     *
     * @param opinion
     */
    void saveFeedback(final MemberOpinion opinion);
}