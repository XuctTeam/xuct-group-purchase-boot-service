/**
 * Copyright (C), 2015-2023, XXX有限公司
 * FileName: MemberWaresCollectService
 * Author:   Derek Xu
 * Date:     2023/3/28 17:39
 * Description:
 * History:
 * <author>          <time>          <version>          <desc>
 * Derek Xu         修改时间           版本号              描述
 */
package cn.com.xuct.group.purchase.service;

import cn.com.xuct.group.purchase.base.service.IBaseService;
import cn.com.xuct.group.purchase.entity.MemberWaresCollect;
import cn.com.xuct.group.purchase.mapper.MemberWaresCollectMapper;
import cn.com.xuct.group.purchase.vo.result.MemberWaresResult;

import java.util.List;

/**
 * 〈一句话功能简述〉<br>
 * 〈〉
 *
 * @author Derek Xu
 * @create 2023/3/28
 * @since 1.0.0
 */
public interface MemberWaresCollectService extends IBaseService<MemberWaresCollectMapper, MemberWaresCollect> {

    /**
     * 收藏或取消收藏
     *
     * @param waresId
     * @param memberId
     */
    void collect(final Long memberId, final Long waresId);

    /**
     * 用户收藏列表
     *
     * @param memberId
     * @return
     */
    List<MemberWaresResult> list(final Long memberId);
}