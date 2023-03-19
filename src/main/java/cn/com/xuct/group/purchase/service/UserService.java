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

/**
 * 〈一句话功能简述〉<br> 
 * 〈〉
 *
 * @author Derek Xu
 * @create 2023/3/18
 * @since 1.0.0
 */
public interface UserService extends IBaseService<UserMapper, User> {

    User findByOpenId(final String openId);
}