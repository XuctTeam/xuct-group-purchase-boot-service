/**
 * Copyright (C), 2015-2023, 楚恬商场
 * FileName: RoleServiceImpl
 * Author:   Derek Xu
 * Date:     2023/3/18 23:09
 * Description:
 * History:
 * <author>          <time>          <version>          <desc>
 * Derek Xu           修改时间           版本号              描述
 */
package cn.com.xuct.group.purchase.service.impl;

import cn.com.xuct.group.purchase.base.enums.ColumnEnum;
import cn.com.xuct.group.purchase.base.service.BaseServiceImpl;
import cn.com.xuct.group.purchase.base.vo.Column;
import cn.com.xuct.group.purchase.constants.RedisCacheConstants;
import cn.com.xuct.group.purchase.constants.RoleCodeEnum;
import cn.com.xuct.group.purchase.entity.Resource;
import cn.com.xuct.group.purchase.entity.Role;
import cn.com.xuct.group.purchase.entity.RoleResource;
import cn.com.xuct.group.purchase.entity.User;
import cn.com.xuct.group.purchase.mapper.RoleMapper;
import cn.com.xuct.group.purchase.mapper.UserMapper;
import cn.com.xuct.group.purchase.service.RoleResourceService;
import cn.com.xuct.group.purchase.service.RoleService;
import cn.com.xuct.group.purchase.vo.dto.ResourceButtonDto;
import cn.com.xuct.group.purchase.vo.result.admin.AdminMenuResult;
import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;

import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * 〈一句话功能简述〉<br>
 * 〈〉
 *
 * @author Derek Xu
 * @create 2023/3/18
 * @since 1.0.0
 */
@Slf4j
@Service
@RequiredArgsConstructor
public class RoleServiceImpl extends BaseServiceImpl<RoleMapper, Role> implements RoleService {

    private final UserMapper userMapper;

    private final RoleMapper roleMapper;

    private final RoleResourceService roleResourceService;

    @Override
    @Cacheable(cacheNames = RedisCacheConstants.USER_CACHE_ROLE_NAME, key = "#id", unless = "#result == null")
    public Role findById(final Long id) {
        return roleMapper.selectById(id);
    }

    @Override
    public List<AdminMenuResult> getUserMenuList(Long userId) {
        User user = userMapper.selectById(userId);
        if (user == null) {
            return null;
        }
        List<Resource> resources = roleMapper.getUserMenuList(user.getRoleId());
        return this.packageResourceToMenu(resources);
    }

    @Override
    public Map<String, List<String>> getUserButtonList(Long userId) {
        User user = userMapper.selectById(userId);
        if (user == null) {
            return null;
        }
        List<ResourceButtonDto> resourceButtonDos = roleMapper.getUserButtonList(user.getRoleId());
        if (CollectionUtils.isEmpty(resourceButtonDos)) {
            return Maps.newHashMap();
        }
        return resourceButtonDos.stream().collect(Collectors.groupingBy(ResourceButtonDto::getPathName, Collectors.mapping(ResourceButtonDto::getPerm, Collectors.toList())));
    }

    @Override
    public List<AdminMenuResult> packageResourceToMenu(List<Resource> resources) {
        List<AdminMenuResult> rootMenus = Lists.newArrayList();
        resources.stream().filter(item -> item.getParentId() == -1).sorted(Comparator.comparing(Resource::getSort)).toList().forEach(item -> {
            AdminMenuResult result = getMenuResult(item);
            result.setChildren(this.recursionResource(item.getId(), resources));
            rootMenus.add(result);
        });
        return rootMenus;
    }

    @Override
    public void deleteRoleResourceByResourceIds(List<Long> resourceIds) {
        Map<String, Object> resourceMaps = Maps.newHashMap();
        for (Long id : resourceIds) {
            resourceMaps.put("resource_id", id);
        }
        roleResourceService.removeByMap(resourceMaps);
    }

    @Override
    public List<Role> findRoleList() {
        return this.find(Lists.newArrayList(Column.of("code", RoleCodeEnum.member.name(), ColumnEnum.nq)));
    }

    @Override
    public List<String> getRoleResourceIds(Long roleId) {
        return roleResourceService.find(Column.of("role_id", roleId))
                .stream().map(RoleResource::getResourceId).map(String::valueOf).collect(Collectors.toList());
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void bindRoleResourceIds(final Long roleId, final List<Long> resourceIds) {
        roleResourceService.delete(Column.of("role_id", roleId));
        if (CollectionUtils.isEmpty(resourceIds)) {
            return;
        }
        List<RoleResource> roleResources = Lists.newArrayList();
        RoleResource roleResource = null;
        for (Long resourceId : resourceIds) {
            if (resourceId == -1L) {
                continue;
            }
            roleResource = new RoleResource();
            roleResource.setRoleId(roleId);
            roleResource.setResourceId(resourceId);
            roleResources.add(roleResource);
        }
        roleResourceService.saveBatch(roleResources);
    }

    private List<AdminMenuResult> recursionResource(Long pid, List<Resource> allResources) {
        //子菜单
        List<Resource> childList = Lists.newArrayList();
        for (Resource nav : allResources) {
            //遍历所有节点，将所有菜单的父id与传过来的根节点的id比较
            //相等说明：为该根节点的子节点
            if (nav.getParentId().toString().equals(String.valueOf(pid))) {
                childList.add(nav);
            }
        }
        List<AdminMenuResult> childMenuList = Lists.newArrayList();
        //递归设置子节点
        for (Resource nav : childList) {
            AdminMenuResult result = this.getMenuResult(nav);
            result.setChildren(recursionResource(nav.getId(), allResources));
            childMenuList.add(result);
        }
        childMenuList.sort(Comparator.comparing(AdminMenuResult::getSort));
        //如果节点下没有子节点，返回一个空List（递归退出）
        if (childMenuList.size() == 0) {
            return Lists.newArrayList();
        }
        return childMenuList;
    }

    /**
     * 封装用户菜单
     *
     * @param item
     * @return cn.com.xuct.group.purchase.vo.result.admin.AdminMenuResult
     * @return:
     * @since: 1.0.0
     * @Author:Derek xu
     * @Date: 2023/5/10 12:02
     */
    private AdminMenuResult getMenuResult(Resource item) {
        AdminMenuResult result = new AdminMenuResult();
        BeanUtils.copyProperties(item, result);
        result.setName(item.getPathName());
        AdminMenuResult.Meta meta = new AdminMenuResult.Meta();
        meta.setIcon(item.getIcon());
        meta.setTitle(item.getTitle());
        BeanUtils.copyProperties(item, meta);
        result.setMeta(meta);
        result.setChildren(Lists.newArrayList());
        return result;
    }


}