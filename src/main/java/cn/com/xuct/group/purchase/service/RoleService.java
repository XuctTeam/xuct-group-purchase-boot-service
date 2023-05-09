/**
 * Copyright (C), 2015-2023, 楚恬商场
 * FileName: RoleService
 * Author:   Derek Xu
 * Date:     2023/3/18 23:08
 * Description:
 * History:
 * <author>          <time>          <version>          <desc>
 * Derek Xu           修改时间           版本号              描述
 */
package cn.com.xuct.group.purchase.service;

import cn.com.xuct.group.purchase.base.service.IBaseService;
import cn.com.xuct.group.purchase.entity.Role;
import cn.com.xuct.group.purchase.mapper.RoleMapper;
import cn.com.xuct.group.purchase.vo.result.AdminMenuResult;

import java.util.List;

/**
 * 〈一句话功能简述〉<br> 
 * 〈〉
 *
 * @author Derek Xu
 * @create 2023/3/18
 * @since 1.0.0
 */
public interface RoleService extends IBaseService<RoleMapper , Role> {


    Role findById(final Long id);

    /**
     * 菜单
     * @param userId
     * @return
     */
    List<AdminMenuResult> menuList(final Long userId);
}