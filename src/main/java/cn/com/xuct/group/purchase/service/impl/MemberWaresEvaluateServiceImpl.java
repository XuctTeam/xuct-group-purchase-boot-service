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
import cn.com.xuct.group.purchase.entity.*;
import cn.com.xuct.group.purchase.mapper.MemberWaresEvaluateMapper;
import cn.com.xuct.group.purchase.service.MemberOrderItemService;
import cn.com.xuct.group.purchase.service.MemberWaresEvaluateService;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.github.yulichang.wrapper.MPJLambdaWrapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

import java.util.List;

/**
 * 〈一句话功能简述〉<br>
 * 〈〉
 *
 * @author Derek Xu
 * @create 2023/4/23
 * @since 1.0.0
 */
@Slf4j
@Service
@RequiredArgsConstructor
public class MemberWaresEvaluateServiceImpl extends BaseServiceImpl<MemberWaresEvaluateMapper, MemberWaresEvaluate> implements MemberWaresEvaluateService {

    private final MemberOrderItemService memberOrderItemService;

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

    @Override
    public List<MemberOrderItem> evaluateList(Long memberId) {
        return memberOrderItemService.queryEvaluateByMemberId(memberId);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void evaluateWares(Long memberId, Long orderItemId, String rate, String evaluateImages, String remarks) {
        MemberOrderItem item = memberOrderItemService.getById(orderItemId);
        if (item == null) {
            log.error("MemberOrderServiceImpl:: save evaluate error , order item id = {}", orderItemId);
            return;
        }
        MemberWaresEvaluate evaluate = new MemberWaresEvaluate();
        evaluate.setMemberId(memberId);
        evaluate.setOrderItemId(orderItemId);
        evaluate.setWaresId(item.getWaresId());
        evaluate.setRate(rate);
        if (StringUtils.hasLength(evaluateImages)) {
            evaluate.setEvaluateImages(evaluateImages);
        }
        if (StringUtils.hasLength(remarks)) {
            evaluate.setRemarks(remarks);
        }
        this.save(evaluate);
        item.setEvaluation(true);
        memberOrderItemService.updateById(item);
    }
}