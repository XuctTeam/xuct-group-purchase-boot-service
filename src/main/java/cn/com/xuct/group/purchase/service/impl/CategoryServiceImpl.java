/**
 * Copyright (C), 2015-2023, 263企业通信
 * FileName: CategoryServiceImpl
 * Author:   Derek Xu
 * Date:     2023/5/17 9:29
 * Description:
 * History:
 * <author>          <time>          <version>          <desc>
 * 作者姓名           修改时间           版本号              描述
 */
package cn.com.xuct.group.purchase.service.impl;

import cn.com.xuct.group.purchase.base.service.BaseServiceImpl;
import cn.com.xuct.group.purchase.entity.Category;
import cn.com.xuct.group.purchase.mapper.CategoryMapper;
import cn.com.xuct.group.purchase.service.CategoryService;
import cn.com.xuct.group.purchase.service.WaresService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * 〈一句话功能简述〉<br> 
 * 〈〉
 *
 * @author Derek Xu
 * @create 2023/5/17
 * @since 1.0.0
 */
@Slf4j
@Service
@RequiredArgsConstructor
public class CategoryServiceImpl extends BaseServiceImpl<CategoryMapper , Category> implements CategoryService {

    private final WaresService waresService;

    @Transactional(rollbackFor = Exception.class)
    @Override
    public void deleteCategory(Long id) {
        this.removeById(id);
        waresService.removeWaresCategoryId(id);
    }
}