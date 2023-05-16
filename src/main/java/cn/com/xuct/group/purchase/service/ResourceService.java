/**
 * Copyright (C), 2015-2023, 263企业通信
 * FileName: ResourceService
 * Author:   Derek Xu
 * Date:     2023/5/10 16:21
 * Description:
 * History:
 * <author>          <time>          <version>          <desc>
 * 作者姓名           修改时间           版本号              描述
 */
package cn.com.xuct.group.purchase.service;

import cn.com.xuct.group.purchase.base.service.IBaseService;
import cn.com.xuct.group.purchase.entity.Resource;
import cn.com.xuct.group.purchase.mapper.ResourceMapper;
import cn.com.xuct.group.purchase.vo.result.admin.AdminMenuResult;
import cn.com.xuct.group.purchase.vo.result.admin.AdminMenuTreeResult;

import java.util.List;

/**
 * 〈一句话功能简述〉<br>
 * 〈〉
 *
 * @author Derek Xu
 * @create 2023/5/10
 * @since 1.0.0
 */
public interface ResourceService extends IBaseService<ResourceMapper, Resource> {

    /**
     * 树型菜单
     *
     * @param showBtn
     * @return
     */
    List<AdminMenuTreeResult> menuTreeList(final Integer showBtn);


    List<AdminMenuResult> menuList();

    /**
     * @param resource
     */
    boolean addOrUpdateResource(Resource resource);

    /**
     * shanc
     *
     * @param resourceId
     * @return
     */
    boolean deleteResource(final Long resourceId);
}