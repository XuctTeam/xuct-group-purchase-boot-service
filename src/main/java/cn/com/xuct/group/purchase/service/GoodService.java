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
import cn.com.xuct.group.purchase.entity.Good;
import cn.com.xuct.group.purchase.mapper.GoodMapper;
import cn.com.xuct.group.purchase.vo.result.GoodResult;

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
public interface GoodService extends IBaseService<GoodMapper, Good> {

    /**
     * 获取获取详情
     *
     * @param id
     * @param memberId
     * @return cn.com.xuct.group.purchase.vo.result.GoodResult
     */
    GoodResult getGood(final Long id, final Long memberId);

    /**
     * 查询列表
     *
     * @return java.lang.List
     */
    List<Good> findList();

    /**
     * 根据商品ID更新库存
     *
     * @param goodIds
     */
    void updateGoodInventory(Map<Long, Integer> inventoryMap);

    /**
     * @param name
     * @param pageNum
     * @param pageSize
     * @return
     */
    PageData<Good> pageGoods(final String name, final Integer pageNum, final Integer pageSize);

    /**
     * 修改商品状态
     *
     * @param goodId
     * @param status
     */
    void changeGoodStatus(final Long goodId, final Integer status);

    /**
     * 删除商品
     *
     * @param goodId
     */
    void deleteGood(final Long goodId);

    /**
     * 添加商品
     *
     * @param good
     */
    int addGood(final Good good);

    /**
     * 编辑商品
     *
     * @param good
     */
    int editGood(final Good good);
}