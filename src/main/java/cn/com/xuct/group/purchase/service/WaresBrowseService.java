/**
 * Copyright (C), 2015-2023, XXX有限公司
 * FileName: WaresBrowseService
 * Author:   Derek Xu
 * Date:     2023/3/29 11:10
 * Description:
 * History:
 * <author>          <time>          <version>          <desc>
 * Derek Xu         修改时间           版本号              描述
 */
package cn.com.xuct.group.purchase.service;

import cn.com.xuct.group.purchase.base.service.IBaseService;
import cn.com.xuct.group.purchase.entity.WaresBrowse;
import cn.com.xuct.group.purchase.mapper.WaresBrowseMapper;

/**
 * 〈一句话功能简述〉<br> 
 * 〈〉
 *
 * @author Derek Xu
 * @create 2023/3/29
 * @since 1.0.0
 */
public interface WaresBrowseService extends IBaseService<WaresBrowseMapper, WaresBrowse> {

    /**
     * 收藏或取消收藏
     *
     * @param memberId
     * @param waresId
     */
    void browse(final Long memberId ,  final Long waresId);
}