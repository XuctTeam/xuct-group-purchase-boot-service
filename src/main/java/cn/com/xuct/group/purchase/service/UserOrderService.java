/**
 * Copyright (C), 2015-2023, 263企业通信
 * FileName: UserOrderService
 * Author:   Derek Xu
 * Date:     2023/4/7 11:19
 * Description:
 * History:
 * <author>          <time>          <version>          <desc>
 * 作者姓名           修改时间           版本号              描述
 */
package cn.com.xuct.group.purchase.service;

import cn.com.xuct.group.purchase.base.service.IBaseService;
import cn.com.xuct.group.purchase.entity.UserOrder;
import cn.com.xuct.group.purchase.mapper.UserOrderMapper;

import java.util.List;

/**
 * 〈一句话功能简述〉<br>
 * 〈〉
 *
 * @author Derek Xu
 * @create 2023/4/7
 * @since 1.0.0
 */
public interface UserOrderService extends IBaseService<UserOrderMapper, UserOrder> {


    /**
     * 保存订单
     *
     * @param userId
     * @param addressId 配置地址
     * @param integral
     * @param remarks
     * @param goodIds
     */
    int saveOrder(final Long userId, Long addressId, Integer integral, String remarks, List<Long> goodIds);
}