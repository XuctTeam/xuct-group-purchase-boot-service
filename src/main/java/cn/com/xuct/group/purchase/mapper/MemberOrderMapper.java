/**
 * Copyright (C), 2015-2023, 263企业通信
 * FileName: UserOrderMapper
 * Author:   Derek Xu
 * Date:     2023/4/7 11:18
 * Description:
 * History:
 * <author>          <time>          <version>          <desc>
 * 作者姓名           修改时间           版本号              描述
 */
package cn.com.xuct.group.purchase.mapper;

import cn.com.xuct.group.purchase.entity.MemberOrder;
import cn.com.xuct.group.purchase.vo.result.OrderResult;
import cn.com.xuct.group.purchase.vo.result.OrderSumResult;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import org.apache.ibatis.annotations.Param;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

/**
 * 〈一句话功能简述〉<br>
 * 〈〉
 *
 * @author Derek Xu
 * @create 2023/4/7
 * @since 1.0.0
 */
public interface MemberOrderMapper extends BaseMapper<MemberOrder> {


    OrderSumResult sumCount(@Param("memberId") Long memberId);

    /**
     * 分页查哈讯
     *
     * @param memberId
     * @param status
     * @param page
     * @param refundStatus
     * @return
     */
    IPage<MemberOrder> findMemberOrder(@Param("memberId") Long memberId, @Param("status") Integer status, Page<MemberOrder> page, @Param("refundStatus") Integer refundStatus);


    /**
     * 通过关键词查询
     *
     * @param memberId
     * @param status
     * @param page
     * @param refund        是否是售后搜索
     * @param wordOrderId
     * @param wordWaresName
     * @return
     */
    IPage<MemberOrder> search(@Param("memberId") Long memberId, Page<MemberOrder> page, @Param("refund") int refund, @Param("wordOrderId") String wordOrderId, @Param("wordWaresName") String wordWaresName);


    /**
     * 获取订单详情
     *
     * @param orderId
     * @return
     */
    OrderResult getOrderDetail(@RequestParam("orderId") Long orderId);

    /**
     * 删除订单列表
     *
     * @param memberId
     * @return
     */
    List<MemberOrder> deleteList(@Param("memberId") Long memberId);

    /**
     * 查询所有订单
     *
     * @return
     */
    IPage<OrderResult> findAllMemberOrder(@Param("status") Integer status, @Param("createTime") List<String> createTime, Page<MemberOrder> page);
}