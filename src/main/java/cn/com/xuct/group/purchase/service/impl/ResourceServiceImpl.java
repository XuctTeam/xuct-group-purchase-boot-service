/**
 * Copyright (C), 2015-2023, 263企业通信
 * FileName: ResourceServiceImpl
 * Author:   Derek Xu
 * Date:     2023/5/10 16:22
 * Description:
 * History:
 * <author>          <time>          <version>          <desc>
 * 作者姓名           修改时间           版本号              描述
 */
package cn.com.xuct.group.purchase.service.impl;

import cn.com.xuct.group.purchase.base.enums.ColumnEnum;
import cn.com.xuct.group.purchase.base.service.BaseServiceImpl;
import cn.com.xuct.group.purchase.base.vo.Column;
import cn.com.xuct.group.purchase.constants.CategoryConstants;
import cn.com.xuct.group.purchase.entity.Resource;
import cn.com.xuct.group.purchase.entity.Role;
import cn.com.xuct.group.purchase.entity.RoleResource;
import cn.com.xuct.group.purchase.entity.User;
import cn.com.xuct.group.purchase.mapper.ResourceMapper;
import cn.com.xuct.group.purchase.mapstruct.IAdminMenuConvert;
import cn.com.xuct.group.purchase.service.ResourceService;
import cn.com.xuct.group.purchase.service.RoleService;
import cn.com.xuct.group.purchase.vo.result.admin.AdminMenuResult;
import cn.com.xuct.group.purchase.vo.result.admin.AdminMenuTreeResult;
import com.github.yulichang.wrapper.MPJLambdaWrapper;
import com.google.common.collect.Lists;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;

import java.util.Comparator;
import java.util.List;

/**
 * 〈一句话功能简述〉<br>
 * 〈〉
 *
 * @author Derek Xu
 * @create 2023/5/10
 * @since 1.0.0
 */
@Slf4j
@Service
@RequiredArgsConstructor
public class ResourceServiceImpl extends BaseServiceImpl<ResourceMapper, Resource> implements ResourceService {

    private final RoleService roleService;

    @Override
    @SuppressWarnings("all")
    public boolean addOrUpdateResource(Resource resource) {
        /* 顶级菜单不允许添加按钮 */
        if (resource.getParentId() == -1 && CategoryConstants.BUTTON.equals(resource.getCategory())) {
            return false;
        }
        Resource parentResource = null;
        if (resource.getParentId() != -1) {
            parentResource = this.getById(resource.getParentId());
            if (parentResource != null) {
                resource.setLevel(parentResource.getLevel() + 1);
            }
        } else {
            resource.setLevel(0);
        }
        if (resource.getId() == null && CategoryConstants.BUTTON.equals(resource.getCategory()) && parentResource != null) {
            resource.setPath(parentResource.getPath());
            resource.setPathName(parentResource.getPathName());
        }
        return this.saveOrUpdate(resource);
    }

    @Transactional(rollbackFor = Exception.class)
    @Override
    public boolean deleteResource(Long resourceId) {
        List<Long> idsList = Lists.newArrayList();
        this.selectChildListById(resourceId, idsList);
        idsList.add(resourceId);
        /* 删除角色绑定资源 */
        roleService.deleteRoleResourceByResourceIds(idsList);
        return this.removeByIds(idsList);
    }

    @Override
    public List<Resource> findUserPermissionList(Long roleId) {
        MPJLambdaWrapper<Resource> qr = new MPJLambdaWrapper<Resource>()
                .selectAll(Resource.class)
                .leftJoin(RoleResource.class, RoleResource::getResourceId, Resource::getId)
                .eq(RoleResource::getRoleId, roleId)
                .eq(Resource::getCategory, CategoryConstants.BUTTON);
        return this.list(qr);

    }

    @Override
    public List<AdminMenuTreeResult> menuTreeList(final Integer showBtn) {
        List<Resource> resources = this.resourceList(showBtn != null && showBtn == 1 ? 0 : 1);
        List<AdminMenuTreeResult> rootMenus = Lists.newArrayList();
        resources.stream().filter(item -> item.getParentId() == -1).sorted(Comparator.comparing(Resource::getSort)).toList().forEach(item -> {
            AdminMenuTreeResult result = this.getTreeMenuResult(item);
            result.setChildren(this.recurseTreeResource(item.getId(), resources));
            rootMenus.add(result);
        });
        return rootMenus;
    }

    @Override
    public List<AdminMenuResult> menuList() {
        return roleService.packageResourceToMenu(this.resourceList(0));
    }

    private List<Resource> resourceList(final Integer isTree) {
        if (isTree == 1) {
            return this.find(Column.of("category", CategoryConstants.BUTTON, ColumnEnum.nq));
        }
        return this.list();
    }

    /**
     * 递归封装树型菜单
     *
     * @param pid
     * @param allResources
     * @return java.util.List<cn.com.xuct.group.purchase.vo.result.admin.AdminMenuTreeResult>
     * @return:
     * @since: 1.0.0
     * @Author:Derek xu
     * @Date: 2023/5/10 13:06
     */
    private List<AdminMenuTreeResult> recurseTreeResource(Long pid, List<Resource> allResources) {
        List<Resource> childList = Lists.newArrayList();
        for (Resource nav : allResources) {
            if (nav.getParentId().toString().equals(String.valueOf(pid))) {
                childList.add(nav);
            }
        }
        List<AdminMenuTreeResult> childMenuList = Lists.newArrayList();
        for (Resource nav : childList) {
            AdminMenuTreeResult result = this.getTreeMenuResult(nav);
            result.setChildren(recurseTreeResource(nav.getId(), allResources));
            childMenuList.add(result);
        }
        childMenuList.sort(Comparator.comparing(AdminMenuTreeResult::getSort));
        if (childMenuList.size() == 0) {
            return Lists.newArrayList();
        }
        return childMenuList;
    }


    /**
     * 封装树型菜单
     *
     * @param resource
     * @return cn.com.xuct.group.purchase.vo.result.admin.AdminMenuTreeResult
     * @return:
     * @since: 1.0.0
     * @Author:Derek xu
     * @Date: 2023/5/10 12:01
     */
    private AdminMenuTreeResult getTreeMenuResult(Resource resource) {
        return IAdminMenuConvert.INSTANCE.resource2TreeMenu(resource);
    }


    /**
     * @param pid
     * @param idList
     * @return:
     * @since: 1.0.0
     * @Author:Derek xu
     * @Date: 2023/5/11 10:55
     */
    private void selectChildListById(Long pid, List<Long> idList) {
        List<Resource> resources = this.find(Column.of("parent_id", pid));
        if (CollectionUtils.isEmpty(resources)) {
            return;
        }
        idList.addAll(resources.stream().map(Resource::getId).toList());
    }
}