/**
 * Copyright (C), 2015-2023, XXX有限公司
 * FileName: WaresBrowseServiceImpl
 * Author:   Derek Xu
 * Date:     2023/3/29 11:10
 * Description:
 * History:
 * <author>          <time>          <version>          <desc>
 * Derek Xu         修改时间           版本号              描述
 */
package cn.com.xuct.group.purchase.service.impl;

import cn.com.xuct.group.purchase.base.service.BaseServiceImpl;
import cn.com.xuct.group.purchase.entity.WaresBrowse;
import cn.com.xuct.group.purchase.mapper.WaresBrowseMapper;
import cn.com.xuct.group.purchase.service.WaresBrowseService;
import cn.com.xuct.group.purchase.service.MemberBrowseService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

/**
 * 〈一句话功能简述〉<br>
 * 〈〉
 *
 * @author Derek Xu
 * @create 2023/3/29
 * @since 1.0.0
 */
@Slf4j
@Service
@RequiredArgsConstructor
public class WaresBrowseServiceImpl extends BaseServiceImpl<WaresBrowseMapper, WaresBrowse> implements WaresBrowseService {

    private final MemberBrowseService memberBrowseService;

    @Override
    public void browse(final Long memberId , Long waresId) {
        ((WaresBrowseMapper)this.getBaseMapper()).browse(waresId);
        if(memberId == null){
            return;
        }
        memberBrowseService.addUserBrowse(memberId , waresId);
    }
}