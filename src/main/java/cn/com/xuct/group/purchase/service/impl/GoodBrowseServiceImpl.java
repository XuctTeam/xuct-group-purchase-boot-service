/**
 * Copyright (C), 2015-2023, XXX有限公司
 * FileName: GoodBrowseServiceImpl
 * Author:   Derek Xu
 * Date:     2023/3/29 11:10
 * Description:
 * History:
 * <author>          <time>          <version>          <desc>
 * Derek Xu         修改时间           版本号              描述
 */
package cn.com.xuct.group.purchase.service.impl;

import cn.com.xuct.group.purchase.base.service.BaseServiceImpl;
import cn.com.xuct.group.purchase.entity.GoodBrowse;
import cn.com.xuct.group.purchase.mapper.GoodBrowseMapper;
import cn.com.xuct.group.purchase.service.GoodBrowseService;
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
public class GoodBrowseServiceImpl extends BaseServiceImpl<GoodBrowseMapper, GoodBrowse> implements GoodBrowseService {

    @Override
    public void browse(Long goodId) {
        ((GoodBrowseMapper)this.getBaseMapper()).browse(goodId);
    }
}