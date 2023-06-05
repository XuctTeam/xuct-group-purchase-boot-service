/**
 * Copyright (C), 2015-2023, 楚恬商场
 * FileName: UserServiceImpl
 * Author:   Derek Xu
 * Date:     2023/3/18 14:19
 * Description:
 * History:
 * <author>          <time>          <version>          <desc>
 * Derek Xu           修改时间           版本号              描述
 */
package cn.com.xuct.group.purchase.service.impl;

import cn.com.xuct.group.purchase.base.enums.ColumnEnum;
import cn.com.xuct.group.purchase.base.enums.SortEnum;
import cn.com.xuct.group.purchase.base.service.BaseServiceImpl;
import cn.com.xuct.group.purchase.base.vo.Column;
import cn.com.xuct.group.purchase.base.vo.PageData;
import cn.com.xuct.group.purchase.base.vo.Sort;
import cn.com.xuct.group.purchase.constants.RedisCacheConstants;
import cn.com.xuct.group.purchase.entity.Member;
import cn.com.xuct.group.purchase.mapper.MemberMapper;
import cn.com.xuct.group.purchase.service.MemberService;
import cn.com.xuct.group.purchase.vo.result.MemberSumResult;
import cn.hutool.core.date.DateTime;
import cn.hutool.core.date.DateUtil;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.google.common.collect.Lists;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;
import org.springframework.util.StringUtils;

import java.util.List;

/**
 * 〈一句话功能简述〉<br>
 * 〈〉
 *
 * @author Derek Xu
 * @create 2023/3/18
 * @since 1.0.0
 */
@Slf4j
@Service
@RequiredArgsConstructor
public class MemberServiceImpl extends BaseServiceImpl<MemberMapper, Member> implements MemberService {

    @Override
    public Member findByOpenId(String openId) {
        Member member = super.get(Column.of("open_id", openId));
        if (member == null) {
            member = new Member();
            member.setOpenId(openId);
            super.save(member);
        }
        return member;
    }

    @Override
    @Cacheable(cacheNames = RedisCacheConstants.MEMBER_CACHE_ABLE_CACHE_NAME, key = "#id", unless = "#result == null")
    public Member findById(Long id) {
        return this.getById(id);
    }


    @Override
    @CachePut(cacheNames = RedisCacheConstants.MEMBER_CACHE_ABLE_CACHE_NAME, key = "#member.id", unless = "#result == null")
    public Member updateUserInfo(Member member, String phone, String nickname, String avatar) {
        if (StringUtils.hasLength(phone)) {
            member.setPhone(phone);
        }
        if (StringUtils.hasLength(nickname)) {
            member.setNickname(nickname);
        }
        if (StringUtils.hasLength(avatar)) {
            member.setAvatar(avatar);
        }
        this.updateById(member);
        return member;
    }

    @Override
    @CachePut(cacheNames = RedisCacheConstants.MEMBER_CACHE_ABLE_CACHE_NAME, key = "#memberId", unless = "#result == null")
    public Member updateUserIntegral(Long memberId, Long integral) {
        Member member = this.getById(memberId);
        if (member == null) {
            return null;
        }
        member.setIntegral(member.getIntegral() + integral);
        this.updateById(member);
        return member;
    }

    @Override
    public MemberSumResult memberSum(final Long memberId) {
        return ((MemberMapper) this.getBaseMapper()).memberSum(memberId);
    }

    @Override
    public PageData<Member> findPageList(String nickname, final Integer status, final List<String> createTime, Integer pageNum, Integer pageSize) {
        List<Column> columnList = Lists.newArrayList();
        if (StringUtils.hasLength(nickname)) {
            columnList.add(Column.of("nick_name", nickname, ColumnEnum.like));
        }
        if (status != null) {
            columnList.add(Column.of("status", status));
        }
        if (!CollectionUtils.isEmpty(createTime)) {
            DateTime[] createTimes = {DateUtil.parseDate(createTime.get(0)), DateUtil.parseDate(createTime.get(1))};
            columnList.add(Column.of("create_time", createTimes, ColumnEnum.between));
        }
        return this.convert(this.pages(Page.of(pageNum, pageSize), columnList, Sort.of("id", SortEnum.asc)));
    }

    @Override
    @CacheEvict(cacheNames = RedisCacheConstants.MEMBER_CACHE_ABLE_CACHE_NAME, key = "#memberId")
    public void changeMemberStatus(Long memberId, Integer status) {
        Member member = this.getById(memberId);
        if (member == null) {
            return;
        }
        member.setStatus(status);
        this.updateById(member);
    }
}