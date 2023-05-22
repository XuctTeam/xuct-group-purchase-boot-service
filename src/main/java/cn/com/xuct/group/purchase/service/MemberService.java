/**
 * Copyright (C), 2015-2023, 楚恬商场
 * FileName: UserService
 * Author:   Derek Xu
 * Date:     2023/3/18 14:18
 * Description:
 * History:
 * <author>          <time>          <version>          <desc>
 * Derek Xu           修改时间           版本号              描述
 */
package cn.com.xuct.group.purchase.service;

import cn.com.xuct.group.purchase.base.service.IBaseService;
import cn.com.xuct.group.purchase.base.vo.PageData;
import cn.com.xuct.group.purchase.entity.Member;
import cn.com.xuct.group.purchase.entity.User;
import cn.com.xuct.group.purchase.mapper.MemberMapper;
import cn.com.xuct.group.purchase.vo.result.MemberSumResult;
import org.springframework.data.relational.core.sql.In;

import java.util.List;

/**
 * 〈一句话功能简述〉<br>
 * 〈〉
 *
 * @author Derek Xu
 * @create 2023/3/18
 * @since 1.0.0
 */
public interface MemberService extends IBaseService<MemberMapper, Member> {

    /**
     * 通过OpenId查询
     *
     * @param openId
     * @return
     */
    Member findByOpenId(final String openId);

    /**
     * 通过ID查询
     *
     * @param id
     * @return
     */
    Member findById(final Long id);


    /**
     * 修改用户信息
     *
     * @param member
     * @param phone
     * @param nickname
     * @param avatar
     */
    Member updateUserInfo(Member member, final String phone, final String nickname, final String avatar);

    /**
     * 更新用户积分
     *
     * @param memberId
     * @param integral
     * @return
     */
    Member updateUserIntegral(final Long memberId, final Integer integral);

    /**
     * 用户数统计
     *
     * @param memberId
     * @return
     */
    MemberSumResult memberSum(final Long memberId);


    /**
     * 分页查询会员
     *
     * @param nickname
     * @param status
     * @param createTime
     * @param pageNum
     * @param pageSize
     * @return
     */
    PageData<Member> list(final String nickname, final Integer status, final List<String> createTime, final Integer pageNum, final Integer pageSize);

    /**
     * 修改会员状态
     *
     * @param memberId
     * @param status
     */
    void changeMemberStatus(final Long memberId, final Integer status);


}