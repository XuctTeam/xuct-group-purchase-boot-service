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
import cn.com.xuct.group.purchase.entity.User;
import cn.com.xuct.group.purchase.mapper.UserMapper;
import cn.com.xuct.group.purchase.vo.result.UserSumResult;

/**
 * 〈一句话功能简述〉<br>
 * 〈〉
 *
 * @author Derek Xu
 * @create 2023/3/18
 * @since 1.0.0
 */
public interface UserService extends IBaseService<UserMapper, User> {

    /**
     * 通过OpenId查询
     *
     * @param openId
     * @return
     */
    User findByOpenId(final String openId);

    /**
     * 通过ID查询
     *
     * @param id
     * @return
     */
    User findById(final Long id);

    /**
     * 通过账号查询
     *
     * @param username
     * @return
     */
    User findByUsername(final String username);

    /**
     * 修改用户信息
     *
     * @param user
     * @param phone
     * @param nickname
     * @param avatar
     */
    User updateUserInfo(User user, final String phone, final String nickname, final String avatar);

    /**
     * 更新用户积分
     *
     * @param userId
     * @param integral
     * @return
     */
    User updateUserIntegral(final Long userId, final Integer integral);

    /**
     * 用户数统计
     *
     * @param userId
     * @return
     */
    UserSumResult userSum(final Long userId);


}