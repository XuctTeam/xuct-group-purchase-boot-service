/**
 * Copyright (C), 2015-2023, XXX有限公司
 * FileName: UserGoodCollectService
 * Author:   Derek Xu
 * Date:     2023/3/28 17:39
 * Description:
 * History:
 * <author>          <time>          <version>          <desc>
 * Derek Xu         修改时间           版本号              描述
 */
package cn.com.xuct.group.purchase.service;

import cn.com.xuct.group.purchase.base.service.IBaseService;
import cn.com.xuct.group.purchase.entity.UserGoodCollect;
import cn.com.xuct.group.purchase.mapper.UserGoodCollectMapper;

/**
 * 〈一句话功能简述〉<br> 
 * 〈〉
 *
 * @author Derek Xu
 * @create 2023/3/28
 * @since 1.0.0
 */
public interface UserGoodCollectService extends IBaseService<UserGoodCollectMapper, UserGoodCollect> {

    /**
     * 收藏或取消收藏
     * @param userId
     * @param goodId
     */
    void collect(final Long userId , final Long goodId);
}