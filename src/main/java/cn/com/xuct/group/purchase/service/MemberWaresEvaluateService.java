/**
 * Copyright (C), 2015-2023, 楚恬商场
 * FileName: MemberWaresEvaluateService
 * Author:   Derek Xu
 * Date:     2023/4/23 20:12
 * Description:
 * History:
 * <author>          <time>          <version>          <desc>
 * Derek Xu           修改时间           版本号              描述
 */
package cn.com.xuct.group.purchase.service;

import cn.com.xuct.group.purchase.base.service.IBaseService;
import cn.com.xuct.group.purchase.base.vo.PageData;
import cn.com.xuct.group.purchase.entity.MemberOrderItem;
import cn.com.xuct.group.purchase.entity.MemberWaresEvaluate;
import cn.com.xuct.group.purchase.mapper.MemberWaresEvaluateMapper;

import java.util.List;

/**
 * 〈一句话功能简述〉<br>
 * 〈〉
 *
 * @author Derek Xu
 * @create 2023/4/23
 * @since 1.0.0
 */
public interface MemberWaresEvaluateService extends IBaseService<MemberWaresEvaluateMapper, MemberWaresEvaluate> {


    /**
     * 【会员】待评价商品
     *
     * @param memberId
     * @return
     */
    List<MemberOrderItem> evaluateList(final Long memberId);


    /**
     * 【会员】保存评价
     *
     * @param memberId
     * @param orderItemId
     * @param rate
     * @param evaluateImages
     * @param remarks
     */
    void evaluateWares(final Long memberId, final Long orderItemId, final String rate, final String evaluateImages, final String remarks);

    /**
     * 【会员】商品评价列表
     *
     * @param waresId
     * @param top
     * @return
     */
    List<MemberWaresEvaluate> evaluateWaresList(final Long waresId, final Integer top);

    /**
     * 【管理员】评价商品列表
     *
     * @param waresName
     * @param memberName
     * @param page
     * @param size
     * @return
     */

    PageData<MemberWaresEvaluate> findPageWaresEvaluateList(final String waresName, final String memberName, final Integer page, final Integer size);
}