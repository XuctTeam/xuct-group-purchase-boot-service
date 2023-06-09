/**
 * Copyright (C), 2015-2023, 263企业通信
 * FileName: UserOrderItemMapper
 * Author:   Derek Xu
 * Date:     2023/4/7 11:19
 * Description:
 * History:
 * <author>          <time>          <version>          <desc>
 * 作者姓名           修改时间           版本号              描述
 */
package cn.com.xuct.group.purchase.mapper;

import cn.com.xuct.group.purchase.entity.MemberOrderItem;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * 〈一句话功能简述〉<br>
 * 〈〉
 *
 * @author Derek Xu
 * @create 2023/4/7
 * @since 1.0.0
 */
public interface MemberOrderItemMapper extends BaseMapper<MemberOrderItem> {

    List<MemberOrderItem> getOrderItemByOrderId(@Param("orderId") Long orderId);


    /**
     * 统计待评价商品总数
     *
     * @param memberId
     * @return
     */
    int countEvaluation(@Param("memberId") Long memberId);

    /**
     * 查询评价列表
     *
     * @param memberId
     * @return
     */
    List<MemberOrderItem> queryEvaluateByMemberId(@Param("memberId") Long memberId);
}