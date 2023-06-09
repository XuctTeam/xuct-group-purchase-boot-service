/**
 * Copyright (C), 2015-2023, XXX有限公司
 * FileName: UserCartMapper
 * Author:   Derek Xu
 * Date:     2023/3/29 10:09
 * Description:
 * History:
 * <author>          <time>          <version>          <desc>
 * Derek Xu         修改时间           版本号              描述
 */
package cn.com.xuct.group.purchase.mapper;

import cn.com.xuct.group.purchase.entity.MemberWaresCart;
import cn.com.xuct.group.purchase.vo.result.CartResult;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * 〈一句话功能简述〉<br>
 * 〈〉
 *
 * @author Derek Xu
 * @create 2023/3/29
 * @since 1.0.0
 */
public interface MemberWaresCartMapper extends BaseMapper<MemberWaresCart> {

    /**
     * 添加商品到购物车
     *
     * @param waresId
     * @param memberId
     * @
     */
    void addWaresCart(@Param("waresId") final Long waresId, @Param("memberId") final Long memberId);

    /**
     * @param userId
     * @return 查询购物车列表
     */
    List<CartResult> cartList(@Param("memberId") final Long memberId, @Param("waresIds") List<Long> waresIds);
}