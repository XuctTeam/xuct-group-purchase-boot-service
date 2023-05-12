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
import cn.com.xuct.group.purchase.entity.MemberAddress;
import cn.com.xuct.group.purchase.mapper.MemberAddressMapper;

import java.util.List;

/**
 * 〈一句话功能简述〉<br>
 * 〈〉
 *
 * @author Derek Xu
 * @create 2023/3/23
 * @since 1.0.0
 */
public interface MemberAddressService extends IBaseService<MemberAddressMapper, MemberAddress> {

    /**
     * 通过搜索查询
     *
     * @param memberId
     * @param searchValue
     * @return
     */
    List<MemberAddress> findList(Long memberId, String searchValue);

    /**
     * 保存联系地址
     *
     * @param memberAddress
     */
    void saveAddress(MemberAddress memberAddress);

    /**
     * 获取默认地址
     *
     * @param memberId
     * @return
     */
    MemberAddress getDefault(Long memberId);

    /**
     * 删除地址
     *
     * @param memberId
     * @param addressId
     * @return
     */
    boolean delete(final Long memberId, final Long addressId);
}