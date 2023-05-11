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
import cn.com.xuct.group.purchase.entity.Resource;
import cn.com.xuct.group.purchase.entity.Role;
import cn.com.xuct.group.purchase.mapper.RoleMapper;
import cn.com.xuct.group.purchase.vo.result.admin.AdminMenuResult;

import java.util.List;
import java.util.Map;

/**
 * 〈一句话功能简述〉<br>
 * 〈〉
 *
 * @author Derek Xu
 * @create 2023/3/18
 * @since 1.0.0
 */
public interface RoleService extends IBaseService<RoleMapper, Role> {


    Role findById(final Long id);

    /**
     * 菜单列表
     *
     * @param userId
     * @return
     */
    List<AdminMenuResult> getUserMenuList(final Long userId);


    /**
     * 用户按钮权限
     *
     * @param userId
     * @return
     */
    Map<String, List<String>> getUserButtonList(final Long userId);

    /**
     * 封装资源到菜单
     *
     * @param resources
     * @return
     */
    List<AdminMenuResult> packageResourceToMenu(List<Resource> resources);

    /**
     * 删除角色绑定的资源
     *
     * @param resourceIds
     */
    void deleteRoleResourceByResourceIds(final List<Long> resourceIds);

    /**
     * 查询角色列表
     *
     * @return
     */
    List<Role> findRoleList();

    /**
     * 查询角色对应的资源ID
     *
     * @param roleId
     * @return
     */
    List<String> getRoleResourceIds(final Long roleId);

    /**
     * 角色绑定资源
     *
     * @param roleId
     * @param resourceIds
     * @return
     */
    void bindRoleResourceIds(final Long roleId, final List<Long> resourceIds);

}