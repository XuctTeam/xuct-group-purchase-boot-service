/**
 * Copyright (C), 2015-2023, 263企业通信
 * FileName: UserBrowseService
 * Author:   Derek Xu
 * Date:     2023/4/25 22:54
 * Description:
 * History:
 * <author>          <time>          <version>          <desc>
 * 作者姓名           修改时间           版本号              描述
 */
package cn.com.xuct.group.purchase.service;

import cn.com.xuct.group.purchase.base.service.IBaseService;
import cn.com.xuct.group.purchase.entity.Good;
import cn.com.xuct.group.purchase.entity.UserBrowse;
import cn.com.xuct.group.purchase.mapper.UserBrowseMapper;

import java.util.List;

/**
 * 〈一句话功能简述〉<br>
 * 〈〉
 *
 * @author Derek Xu
 * @create 2023/4/25
 * @since 1.0.0
 */
public interface UserBrowseService extends IBaseService<UserBrowseMapper, UserBrowse> {

    /**
     * 添加用户浏览记录
     *
     * @param userId
     * @param goodId
     */
    void addUserBrowse(final Long userId, final Long goodId);

    /**
     * 用户浏览商品列表
     *
     * @param userId
     * @return
     */
    List<Good> list(final Long userId);

    /**
     * 删除浏览记录
     *
     * @param userId
     * @param goodId
     */
    void delete(final Long userId, final Long goodId);
}