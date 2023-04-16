/**
 * Copyright (C), 2015-2023, 楚恬商场
 * FileName: UserAddressService
 * Author:   Derek Xu
 * Date:     2023/3/23 17:48
 * Description:
 * History:
 * <author>          <time>          <version>          <desc>
 * Derek Xu           修改时间           版本号              描述
 */
package cn.com.xuct.group.purchase.service;

import cn.com.xuct.group.purchase.base.service.IBaseService;
import cn.com.xuct.group.purchase.entity.UserAddress;
import cn.com.xuct.group.purchase.mapper.UserAddressMapper;

import java.util.List;

/**
 * 〈一句话功能简述〉<br>
 * 〈〉
 *
 * @author Derek Xu
 * @create 2023/3/23
 * @since 1.0.0
 */
public interface UserAddressService extends IBaseService<UserAddressMapper, UserAddress> {

    /**
     * 通过搜索查询
     *
     * @param userId
     * @param searchValue
     * @return
     */
    List<UserAddress> findList(Long userId, String searchValue);

    /**
     * 保存联系地址
     *
     * @param userAddress
     */
    void saveAddress(UserAddress userAddress);

    /**
     * 获取默认地址
     *
     * @param userId
     * @return
     */
    UserAddress getDefault(Long userId);

    /**
     * 删除地址
     *
     * @param userId
     * @param addressId
     * @return
     */
    boolean delete(final Long userId, final Long addressId);
}