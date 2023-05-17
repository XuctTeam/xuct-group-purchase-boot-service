/**
 * Copyright (C), 2015-2023, 263企业通信
 * FileName: CategoryService
 * Author:   Derek Xu
 * Date:     2023/5/17 9:29
 * Description:
 * History:
 * <author>          <time>          <version>          <desc>
 * 作者姓名           修改时间           版本号              描述
 */
package cn.com.xuct.group.purchase.service;

import cn.com.xuct.group.purchase.base.service.IBaseService;
import cn.com.xuct.group.purchase.entity.Category;
import cn.com.xuct.group.purchase.mapper.CategoryMapper;

/**
 * 〈一句话功能简述〉<br>
 * 〈〉
 *
 * @author Derek Xu
 * @create 2023/5/17
 * @since 1.0.0
 */
public interface CategoryService extends IBaseService<CategoryMapper, Category> {

    /**
     * 删除分类
     *
     * @param id
     */
    void deleteCategory(final Long id);
}