/**
 * Copyright (C), 2015-2023, 楚恬商场
 * FileName: UserAddressServiceImpl
 * Author:   Derek Xu
 * Date:     2023/3/23 17:49
 * Description:
 * History:
 * <author>          <time>          <version>          <desc>
 * Derek Xu           修改时间           版本号              描述
 */
package cn.com.xuct.group.purchase.service.impl;

import cn.com.xuct.group.purchase.base.enums.SortEnum;
import cn.com.xuct.group.purchase.base.service.BaseServiceImpl;
import cn.com.xuct.group.purchase.base.vo.Column;
import cn.com.xuct.group.purchase.base.vo.Sort;
import cn.com.xuct.group.purchase.entity.UserAddress;
import cn.com.xuct.group.purchase.mapper.UserAddressMapper;
import cn.com.xuct.group.purchase.service.UserAddressService;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import lombok.extern.slf4j.Slf4j;
import org.assertj.core.util.Lists;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;
import org.springframework.util.StringUtils;

import java.util.List;

/**
 * 〈一句话功能简述〉<br>
 * 〈〉
 *
 * @author Derek Xu
 * @create 2023/3/23
 * @since 1.0.0
 */
@Slf4j
@Service
public class UserAddressServiceImpl extends BaseServiceImpl<UserAddressMapper, UserAddress> implements UserAddressService {


    @Override
    public List<UserAddress> findList(Long userId, String searchValue) {

        QueryWrapper<UserAddress> qr = this.getQuery().eq("user_id", userId);
        if (StringUtils.hasLength(searchValue)) {
            qr.and(i -> i.like("user_name", searchValue).or().like("tel_number", searchValue));
        }
        qr.orderByDesc("first_choose");


        return this.getBaseMapper().selectList(qr);
    }

    @Override
    @Transactional
    public void saveAddress(UserAddress userAddress) {
        if (userAddress.getFirstChoose() != null && userAddress.getFirstChoose() == 1) {
            UserAddress firstChoose = this.get(Lists.newArrayList(Column.of("user_id", userAddress.getUserId()), Column.of("first_choose", 1)));
            if (firstChoose != null) {
                firstChoose.setFirstChoose(0);
                this.updateById(firstChoose);
            }
        }
        if (userAddress.getId() != null) {
            this.updateById(userAddress);
            return;
        }
        long count = this.count(Column.of("user_id", userAddress.getUserId()));
        if (count == 0) {
            userAddress.setFirstChoose(1);
        }
        this.save(userAddress);
    }

    @Override
    public UserAddress getDefault(Long userId) {
        UserAddress firstChoose = this.get(Lists.newArrayList(Column.of("user_id", userId), Column.of("first_choose", 1)));
        if (firstChoose != null) {
            return firstChoose;
        }
        UserAddress qr = new UserAddress();
        qr.setUpdateTime(null);
        qr.setCreateTime(null);
        qr.setUserId(userId);
        List<UserAddress> userAddresses = this.list(qr, Sort.of("create_time", SortEnum.desc));
        if(CollectionUtils.isEmpty(userAddresses)){
            return null;
        }
        return userAddresses.get(0);
    }
}