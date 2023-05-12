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
import cn.com.xuct.group.purchase.entity.MemberAddress;
import cn.com.xuct.group.purchase.mapper.MemberAddressMapper;
import cn.com.xuct.group.purchase.service.MemberAddressService;
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
public class MemberAddressServiceImpl extends BaseServiceImpl<MemberAddressMapper, MemberAddress> implements MemberAddressService {


    @Override
    public List<MemberAddress> findList(Long memberId, String searchValue) {

        QueryWrapper<MemberAddress> qr = this.getQuery().eq("member_id", memberId).eq("deleted", false);
        if (StringUtils.hasLength(searchValue)) {
            qr.and(i -> i.like("user_name", searchValue).or().like("tel_number", searchValue));
        }
        qr.orderByDesc("first_choose");


        return this.getBaseMapper().selectList(qr);
    }

    @Override
    @Transactional
    public void saveAddress(MemberAddress memberAddress) {
        if (memberAddress.getFirstChoose() != null && memberAddress.getFirstChoose() == 1) {
            MemberAddress firstChoose = this.get(Lists.newArrayList(Column.of("member_id", memberAddress.getMemberId()), Column.of("first_choose", 1)));
            if (firstChoose != null) {
                firstChoose.setFirstChoose(0);
                this.updateById(firstChoose);
            }
        }
        if (memberAddress.getId() != null) {
            this.updateById(memberAddress);
            return;
        }
        long count = this.count(Column.of("member_id", memberAddress.getMemberId()));
        if (count == 0) {
            memberAddress.setFirstChoose(1);
        }
        this.save(memberAddress);
    }

    @Override
    public MemberAddress getDefault(Long memberId) {
        MemberAddress firstChoose = this.get(Lists.newArrayList(Column.of("member_id", memberId), Column.of("first_choose", 1)));
        if (firstChoose != null) {
            return firstChoose;
        }
        MemberAddress qr = new MemberAddress();
        qr.setUpdateTime(null);
        qr.setCreateTime(null);
        qr.setMemberId(memberId);
        List<MemberAddress> memberAddresses = this.list(qr, Sort.of("create_time", SortEnum.desc));
        if (CollectionUtils.isEmpty(memberAddresses)) {
            return null;
        }
        return memberAddresses.get(0);
    }

    @Override
    public boolean delete(Long memberId, Long addressId) {
        MemberAddress address = this.getById(addressId);
        if (!String.valueOf(address.getMemberId()).equals(String.valueOf(memberId))) {
            return false;
        }
        address.setDeleted(true);
        this.updateById(address);
        return true;
    }
}