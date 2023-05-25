/**
 * Copyright (C), 2015-2023, 263企业通信
 * FileName: UserOpinionServiceImpl
 * Author:   Derek Xu
 * Date:     2023/4/28 18:30
 * Description:
 * History:
 * <author>          <time>          <version>          <desc>
 * 作者姓名           修改时间           版本号              描述
 */
package cn.com.xuct.group.purchase.service.impl;

import cn.com.xuct.group.purchase.base.enums.SortEnum;
import cn.com.xuct.group.purchase.base.service.BaseServiceImpl;
import cn.com.xuct.group.purchase.base.vo.Column;
import cn.com.xuct.group.purchase.base.vo.PageData;
import cn.com.xuct.group.purchase.base.vo.Sort;
import cn.com.xuct.group.purchase.constants.RoleCodeEnum;
import cn.com.xuct.group.purchase.entity.Member;
import cn.com.xuct.group.purchase.entity.MemberOpinion;
import cn.com.xuct.group.purchase.entity.Role;
import cn.com.xuct.group.purchase.entity.User;
import cn.com.xuct.group.purchase.mapper.MemberOpinionMapper;
import cn.com.xuct.group.purchase.mapper.MemberWaresCartMapper;
import cn.com.xuct.group.purchase.service.MemberOpinionService;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.github.yulichang.wrapper.MPJLambdaWrapper;
import com.google.common.collect.Lists;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.util.List;

/**
 * 〈一句话功能简述〉<br>
 * 〈〉
 *
 * @author Derek Xu
 * @create 2023/4/28
 * @since 1.0.0
 */
@Service
public class MemberOpinionServiceImpl extends BaseServiceImpl<MemberOpinionMapper, MemberOpinion> implements MemberOpinionService {

    @Override
    public List<MemberOpinion> list(Long memberId) {
        return this.find(Lists.newArrayList(Column.of("member_id", memberId)), Sort.of("status", SortEnum.desc));
    }

    @Override
    public PageData<MemberOpinion> findPageList(String nickname, Integer status, Integer pageNum, Integer pageSize) {
        MPJLambdaWrapper<MemberOpinion> qr = new MPJLambdaWrapper<MemberOpinion>()
                .selectAll(MemberOpinion.class)//查询user表全部字段
                .selectAs(Member::getNickname, MemberOpinion::getMemberName)
                .selectAs(Member::getAvatar, MemberOpinion::getMemberAvatar)
                .leftJoin(Member.class, Member::getId, MemberOpinion::getMemberId);
        if (StringUtils.hasLength(nickname)) {
            qr.like(Member::getNickname, nickname);
        }
        if (status != null) {
            qr.eq(MemberOpinion::isStatus, status == 0);
        }
        return this.convert(super.getBaseMapper().selectPage(Page.of(pageNum, pageSize), qr));
    }
}