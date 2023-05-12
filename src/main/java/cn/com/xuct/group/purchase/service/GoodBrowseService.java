/**
 * Copyright (C), 2015-2023, XXX有限公司
 * FileName: GoodBrowseService
 * Author:   Derek Xu
 * Date:     2023/3/29 11:10
 * Description:
 * History:
 * <author>          <time>          <version>          <desc>
 * Derek Xu         修改时间           版本号              描述
 */
package cn.com.xuct.group.purchase.service;

import cn.com.xuct.group.purchase.base.service.IBaseService;
import cn.com.xuct.group.purchase.entity.GoodBrowse;
import cn.com.xuct.group.purchase.mapper.GoodBrowseMapper;

/**
 * 〈一句话功能简述〉<br> 
 * 〈〉
 *
 * @author Derek Xu
 * @create 2023/3/29
 * @since 1.0.0
 */
public interface GoodBrowseService extends IBaseService<GoodBrowseMapper, GoodBrowse> {

    /**
     * 收藏或取消收藏
     *
     * @param memberId
     * @param goodId
     */
    void browse(final Long memberId ,  final Long goodId);
}