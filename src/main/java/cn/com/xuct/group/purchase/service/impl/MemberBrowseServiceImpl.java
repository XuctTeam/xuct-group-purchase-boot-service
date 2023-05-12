/**
 * Copyright (C), 2015-2023, 263企业通信
 * FileName: UserBrowseServiceImpl
 * Author:   Derek Xu
 * Date:     2023/4/25 22:55
 * Description:
 * History:
 * <author>          <time>          <version>          <desc>
 * 作者姓名           修改时间           版本号              描述
 */
package cn.com.xuct.group.purchase.service.impl;

import cn.com.xuct.group.purchase.base.service.BaseServiceImpl;
import cn.com.xuct.group.purchase.entity.Good;
import cn.com.xuct.group.purchase.entity.MemberBrowse;
import cn.com.xuct.group.purchase.mapper.MemberBrowseMapper;
import cn.com.xuct.group.purchase.service.MemberBrowseService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;

/**
 * 〈一句话功能简述〉<br>
 * 〈〉
 *
 * @author Derek Xu
 * @create 2023/4/25
 * @since 1.0.0
 */
@Slf4j
@Service
public class MemberBrowseServiceImpl extends BaseServiceImpl<MemberBrowseMapper, MemberBrowse> implements MemberBrowseService {


    @Override
    public void addUserBrowse(Long memberId, Long goodId) {
        ((MemberBrowseMapper) this.getBaseMapper()).addUserBrowse(memberId, goodId);
    }

    @Override
    public List<Good> list(Long memberId) {
        return ((MemberBrowseMapper) this.getBaseMapper()).list(memberId);
    }

    @Override
    public void delete(Long memberId, Long goodId) {
        this.removeByMap(new HashMap<>() {{
            put("member_id", memberId);
            put("good_id", goodId);
        }});
    }
}