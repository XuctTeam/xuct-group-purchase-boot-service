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
import cn.com.xuct.group.purchase.base.vo.Sort;
import cn.com.xuct.group.purchase.entity.MemberOpinion;
import cn.com.xuct.group.purchase.mapper.MemberOpinionMapper;
import cn.com.xuct.group.purchase.service.MemberOpinionService;
import com.google.common.collect.Lists;
import org.springframework.stereotype.Service;

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
}