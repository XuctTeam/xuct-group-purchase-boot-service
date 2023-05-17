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
import cn.com.xuct.group.purchase.base.vo.PageData;
import cn.com.xuct.group.purchase.entity.Wares;
import cn.com.xuct.group.purchase.mapper.WaresMapper;
import cn.com.xuct.group.purchase.vo.result.WaresResult;

import java.util.List;
import java.util.Map;

/**
 * 〈一句话功能简述〉<br>
 * 〈〉
 *
 * @author Derek Xu
 * @create 2023/3/27
 * @since 1.0.0
 */
public interface WaresService extends IBaseService<WaresMapper, Wares> {

    /**
     * 获取获取详情
     *
     * @param id
     * @param memberId
     * @return cn.com.xuct.group.purchase.vo.result.GoodResult
     */
    WaresResult getWareInfo(final Long id, final Long memberId);

    /**
     * 查询列表
     *
     * @return java.lang.List
     */
    List<Wares> findList();

    /**
     * 根据商品ID更新库存
     *
     * @param goodIds
     */
    void updateWaresInventory(Map<Long, Integer> inventoryMap);

    /**
     * 分页商品列表
     *
     * @param name
     * @param status
     * @param pageNum
     * @param pageSize
     * @return
     */
    PageData<Wares> pageWares(final String name, final Integer status, final Integer pageNum, final Integer pageSize);

    /**
     * 修改商品状态
     *
     * @param goodId
     * @param status
     */
    void changeWaresStatus(final Long goodId, final Integer status);

    /**
     * 删除商品
     *
     * @param goodId
     */
    void deleteWares(final Long goodId);

    /**
     * 添加商品
     *
     * @param goods
     */
    int addWares(final Wares goods);

    /**
     * 编辑商品
     *
     * @param goods
     */
    int editWares(final Wares goods);

    /**
     * 删除商品分类
     *
     * @param categoryId
     */
    void removeWaresCategoryId(final Long categoryId);


    /**
     * 商品过期
     *
     * @param waresId
     * @param version
     */
    void waresExpire(final Long waresId, final Integer version);
}