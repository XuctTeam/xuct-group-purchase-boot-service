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

import cn.com.xuct.group.purchase.entity.UserOrder;
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
public interface UserOrderMapper extends BaseMapper<UserOrder> {


    OrderSumResult sumCount(@Param("userId") Long userId);

    /**
     * 分页查哈讯
     *
     * @param userId
     * @param status
     * @param page
     * @param refundStatus 退课状态
     * @param wordOrderId  like 订单id
     * @param wordGoodName like 商品名称
     * @return
     */
    IPage<UserOrder> list(@Param("userId") Long userId, @Param("status") Integer status, Page<UserOrder> page, @Param("refundStatus") Integer refundStatus);


    /**
     * 通过关键词查询
     *
     * @param userId
     * @param status
     * @param page
     * @param refund       是否是售后搜索
     * @param wordOrderId
     * @param wordGoodName
     * @return
     */
    IPage<UserOrder> search(@Param("userId") Long userId, Page<UserOrder> page, @Param("refund") int refund, @Param("wordOrderId") String wordOrderId, @Param("wordGoodName") String wordGoodName);


    /**
     * 获取订单详情
     *
     * @param userId
     * @param orderId
     * @return
     */
    OrderResult getOrderDetail(@RequestParam("orderId") Long orderId);

    /**
     * 删除订单列表
     *
     * @param userId
     * @return
     */
    List<UserOrder> deleteList(@Param("userId") Long userId);
}