/**
 * Copyright (C), 2015-2023, 263企业通信
 * FileName: UserOrderItemService
 * Author:   Derek Xu
 * Date:     2023/4/10 9:11
 * Description:
 * History:
 * <author>          <time>          <version>          <desc>
 * 作者姓名           修改时间           版本号              描述
 */
package cn.com.xuct.group.purchase.service;

import cn.com.xuct.group.purchase.base.service.IBaseService;
import cn.com.xuct.group.purchase.entity.MemberOrderItem;
import cn.com.xuct.group.purchase.mapper.MemberOrderItemMapper;

import java.util.List;

/**
 * 〈一句话功能简述〉<br>
 * 〈〉
 *
 * @author Derek Xu
 * @create 2023/4/10
 * @since 1.0.0
 */
public interface MemberOrderItemService extends IBaseService<MemberOrderItemMapper, MemberOrderItem> {

    /**
     * 待评价商品总数
     *
     * @param memberId
     * @return
     */
    int countEvaluation(final Long memberId);

    /**
     * 通过ID查询待评价列表
     *
     * @param memberId
     * @return
     */
    List<MemberOrderItem> queryEvaluateByMemberId(final Long memberId);
}