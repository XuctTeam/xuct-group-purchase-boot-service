/**
 * Copyright (C), 2015-2023, XXX有限公司
 * FileName: GoodService
 * Author:   Derek Xu
 * Date:     2023/3/27 11:24
 * Description:
 * History:
 * <author>          <time>          <version>          <desc>
 * Derek Xu         修改时间           版本号              描述
 */
package cn.com.xuct.group.purchase.service;

import cn.com.xuct.group.purchase.base.service.IBaseService;
import cn.com.xuct.group.purchase.entity.Good;
import cn.com.xuct.group.purchase.mapper.GoodMapper;
import cn.com.xuct.group.purchase.vo.result.GoodResult;

import java.util.List;

/**
 * 〈一句话功能简述〉<br>
 * 〈〉
 *
 * @author Derek Xu
 * @create 2023/3/27
 * @since 1.0.0
 */
public interface GoodService extends IBaseService<GoodMapper, Good> {

    /**
     * 获取获取详情
     *
     * @param id
     * @param userId
     * @return
     */
    GoodResult getGood(final Long id, final Long userId);

    /**
     * 查询列表
     *
     * @return
     */
    List<Good> findList();
}