/**
 * Copyright (C), 2015-2023, XXX有限公司
 * FileName: UserGoodCollectServiceImpl
 * Author:   Derek Xu
 * Date:     2023/3/28 17:39
 * Description:
 * History:
 * <author>          <time>          <version>          <desc>
 * Derek Xu         修改时间           版本号              描述
 */
package cn.com.xuct.group.purchase.service.impl;

import cn.com.xuct.group.purchase.base.service.BaseServiceImpl;
import cn.com.xuct.group.purchase.base.vo.Column;
import cn.com.xuct.group.purchase.entity.MemberWaresCollect;
import cn.com.xuct.group.purchase.mapper.MemberWaresCollectMapper;
import cn.com.xuct.group.purchase.service.MemberWaresCollectService;
import cn.com.xuct.group.purchase.vo.result.MemberWaresResult;
import com.google.common.collect.Lists;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * 〈一句话功能简述〉<br>
 * 〈〉
 *
 * @author Derek Xu
 * @create 2023/3/28
 * @since 1.0.0
 */
@Service
public class MemberWaresCollectServiceImpl extends BaseServiceImpl<MemberWaresCollectMapper, MemberWaresCollect> implements MemberWaresCollectService {

    @Override
    public void collect(Long memberId, Long goodId) {
        MemberWaresCollect memberWaresCollect = this.get(Lists.newArrayList(Column.of("member_id", memberId), Column.of("wares_id", goodId)));
        if (memberWaresCollect == null) {
            memberWaresCollect = new MemberWaresCollect();
            memberWaresCollect.setMemberId(memberId);
            memberWaresCollect.setGoodId(goodId);
            this.save(memberWaresCollect);
            return;
        }
        this.removeById(memberWaresCollect.getId());
    }

    @Override
    public List<MemberWaresResult> list(Long memberId) {
        return ((MemberWaresCollectMapper) super.getBaseMapper()).queryWaresByMemberId(memberId);
    }
}