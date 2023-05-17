/**
 * Copyright (C), 2015-2023, XXX有限公司
 * FileName: WaresMapper
 * Author:   Derek Xu
 * Date:     2023/3/27 11:24
 * Description:
 * History:
 * <author>          <time>          <version>          <desc>
 * Derek Xu         修改时间           版本号              描述
 */
package cn.com.xuct.group.purchase.mapper;

import cn.com.xuct.group.purchase.entity.Wares;
import cn.com.xuct.group.purchase.vo.dto.WaresInventoryDto;
import cn.com.xuct.group.purchase.vo.result.WaresResult;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * 〈一句话功能简述〉<br>
 * 〈〉
 *
 * @author Derek Xu
 * @create 2023/3/27
 * @since 1.0.0
 */
public interface WaresMapper extends BaseMapper<Wares> {


    /**
     * 获取商品详情
     *
     * @param gid
     * @param memberId
     * @return
     */
    WaresResult getWareInfo(@Param("wareId") Long wareId, @Param("memberId") Long memberId);

    /**
     * 更新商品
     *
     * @param wares
     * @return
     */
    int updateWaresInventory(@Param("wares") List<WaresInventoryDto> wares);

    /**
     * 删除商品分类
     *
     * @param categoryId
     */
    void removeWaresCategoryId(@Param("categoryId") Long categoryId);
}