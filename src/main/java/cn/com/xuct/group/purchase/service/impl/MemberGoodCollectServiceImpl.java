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
import cn.com.xuct.group.purchase.entity.MemberGoodCollect;
import cn.com.xuct.group.purchase.mapper.MemberGoodCollectMapper;
import cn.com.xuct.group.purchase.service.MemberGoodCollectService;
import cn.com.xuct.group.purchase.vo.result.MemberGoodResult;
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
public class MemberGoodCollectServiceImpl extends BaseServiceImpl<MemberGoodCollectMapper, MemberGoodCollect> implements MemberGoodCollectService {

    @Override
    public void collect(Long memberId, Long goodId) {
        MemberGoodCollect memberGoodCollect = this.get(Lists.newArrayList(Column.of("member_id", memberId), Column.of("good_id", goodId)));
        if (memberGoodCollect == null) {
            memberGoodCollect = new MemberGoodCollect();
            memberGoodCollect.setMemberId(memberId);
            memberGoodCollect.setGoodId(goodId);
            this.save(memberGoodCollect);
            return;
        }
        this.removeById(memberGoodCollect.getId());
    }

    @Override
    public List<MemberGoodResult> list(Long memberId) {
        return ((MemberGoodCollectMapper) super.getBaseMapper()).queryGoodByMemberId(memberId);
    }
}