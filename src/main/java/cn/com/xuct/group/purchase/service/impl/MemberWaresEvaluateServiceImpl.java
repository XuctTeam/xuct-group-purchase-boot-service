/**
 * Copyright (C), 2015-2023, 楚恬商场
 * FileName: MemberWaresEvaluateServiceImpl
 * Author:   Derek Xu
 * Date:     2023/4/23 20:13
 * Description:
 * History:
 * <author>          <time>          <version>          <desc>
 * Derek Xu           修改时间           版本号              描述
 */
package cn.com.xuct.group.purchase.service.impl;

import cn.com.xuct.group.purchase.base.service.BaseServiceImpl;
import cn.com.xuct.group.purchase.base.vo.PageData;
import cn.com.xuct.group.purchase.entity.Member;
import cn.com.xuct.group.purchase.entity.MemberOpinion;
import cn.com.xuct.group.purchase.entity.MemberWaresEvaluate;
import cn.com.xuct.group.purchase.entity.Wares;
import cn.com.xuct.group.purchase.mapper.MemberWaresEvaluateMapper;
import cn.com.xuct.group.purchase.service.MemberWaresEvaluateService;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.github.yulichang.wrapper.MPJLambdaWrapper;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

/**
 * 〈一句话功能简述〉<br>
 * 〈〉
 *
 * @author Derek Xu
 * @create 2023/4/23
 * @since 1.0.0
 */
@Service
public class MemberWaresEvaluateServiceImpl extends BaseServiceImpl<MemberWaresEvaluateMapper, MemberWaresEvaluate> implements MemberWaresEvaluateService {


    @Override
    public PageData<MemberWaresEvaluate> findPageWaresEvaluateList(String waresName, String memberName, Integer page, Integer size) {
        MPJLambdaWrapper<MemberWaresEvaluate> qr = new MPJLambdaWrapper<MemberWaresEvaluate>()
                .selectAll(MemberWaresEvaluate.class)//查询user表全部字段
                .selectAs(Member::getNickname, MemberWaresEvaluate::getMemberName)
                .selectAs(Member::getAvatar, MemberWaresEvaluate::getMemberAvatar)
                .selectAs(Wares::getName, MemberWaresEvaluate::getWaresName)
                .selectAs(Wares::getFirstDrawing, MemberWaresEvaluate::getWaresFirstDrawing)
                .leftJoin(Member.class, Member::getId, MemberWaresEvaluate::getMemberId)
                .leftJoin(Wares.class, Wares::getId, MemberWaresEvaluate::getWaresId);
        if (StringUtils.hasLength(waresName)) {
            qr.like(Wares::getName, waresName);
        }
        if (StringUtils.hasLength(memberName)) {
            qr.like(Member::getNickname, memberName);
        }
        return this.convert(super.page(Page.of(page, size), qr));
    }
}