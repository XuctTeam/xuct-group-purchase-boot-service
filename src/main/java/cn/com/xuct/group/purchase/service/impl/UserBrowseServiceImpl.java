/**
 * Copyright (C), 2015-2023, 263企业通信
 * FileName: UserBrowseServiceImpl
 * Author:   Derek Xu
 * Date:     2023/4/25 22:55
 * Description:
 * History:
 * <author>          <time>          <version>          <desc>
 * 作者姓名           修改时间           版本号              描述
 */
package cn.com.xuct.group.purchase.service.impl;

import cn.com.xuct.group.purchase.base.service.BaseServiceImpl;
import cn.com.xuct.group.purchase.entity.Good;
import cn.com.xuct.group.purchase.entity.UserBrowse;
import cn.com.xuct.group.purchase.mapper.UserBrowseMapper;
import cn.com.xuct.group.purchase.service.UserBrowseService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;

/**
 * 〈一句话功能简述〉<br>
 * 〈〉
 *
 * @author Derek Xu
 * @create 2023/4/25
 * @since 1.0.0
 */
@Slf4j
@Service
public class UserBrowseServiceImpl extends BaseServiceImpl<UserBrowseMapper, UserBrowse> implements UserBrowseService {


    @Override
    public void addUserBrowse(Long userId, Long goodId) {
        ((UserBrowseMapper) this.getBaseMapper()).addUserBrowse(userId, goodId);
    }

    @Override
    public List<Good> list(Long userId) {
        return ((UserBrowseMapper) this.getBaseMapper()).list(userId);
    }

    @Override
    public void delete(Long userId, Long goodId) {
        this.removeByMap(new HashMap<>() {{
            put("user_id", userId);
            put("good_id", goodId);
        }});
    }
}