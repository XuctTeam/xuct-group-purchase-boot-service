/**
 * Copyright (C), 2015-2023, 263企业通信
 * FileName: UserOrderItemServiceImpl
 * Author:   Derek Xu
 * Date:     2023/4/10 9:12
 * Description:
 * History:
 * <author>          <time>          <version>          <desc>
 * 作者姓名           修改时间           版本号              描述
 */
package cn.com.xuct.group.purchase.service.impl;

import cn.com.xuct.group.purchase.base.service.BaseServiceImpl;
import cn.com.xuct.group.purchase.entity.MemberOrderItem;
import cn.com.xuct.group.purchase.mapper.MemberOrderItemMapper;
import cn.com.xuct.group.purchase.service.MemberOrderItemService;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * 〈一句话功能简述〉<br>
 * 〈〉
 *
 * @author Derek Xu
 * @create 2023/4/10
 * @since 1.0.0
 */
@Service
public class MemberOrderItemServiceImpl extends BaseServiceImpl<MemberOrderItemMapper, MemberOrderItem> implements MemberOrderItemService {

    @Override
    public int countEvaluation(Long memberId) {
        return ((MemberOrderItemMapper) super.getBaseMapper()).countEvaluation(memberId);
    }

    @Override
    public List<MemberOrderItem> queryEvaluateByMemberId(Long memberId) {
        return ((MemberOrderItemMapper) super.getBaseMapper()).queryEvaluateByMemberId(memberId);
    }
}