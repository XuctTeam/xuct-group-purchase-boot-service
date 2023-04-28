/**
 * Copyright (C), 2015-2023, XXX有限公司
 * FileName: UserGoodCollectServiceImpl
 * Author:   Derek Xu
 * Date:     2023/3/28 17:39
 * Description:
 * History:
 * <author>          <time>          <version>          <desc>
 * Derek Xu         修改时间           版本号              描述
 */
package cn.com.xuct.group.purchase.service.impl;

import cn.com.xuct.group.purchase.base.service.BaseServiceImpl;
import cn.com.xuct.group.purchase.base.vo.Column;
import cn.com.xuct.group.purchase.entity.Good;
import cn.com.xuct.group.purchase.entity.UserCoupon;
import cn.com.xuct.group.purchase.entity.UserGoodCollect;
import cn.com.xuct.group.purchase.mapper.UserGoodCollectMapper;
import cn.com.xuct.group.purchase.service.UserGoodCollectService;
import cn.com.xuct.group.purchase.vo.result.UserGoodResult;
import cn.dev33.satoken.stp.StpUtil;
import com.github.yulichang.wrapper.MPJLambdaWrapper;
import com.google.common.collect.Lists;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * 〈一句话功能简述〉<br>
 * 〈〉
 *
 * @author Derek Xu
 * @create 2023/3/28
 * @since 1.0.0
 */
@Service
public class UserGoodCollectServiceImpl extends BaseServiceImpl<UserGoodCollectMapper, UserGoodCollect> implements UserGoodCollectService {

    @Override
    public void collect(Long userId, Long goodId) {
        UserGoodCollect userGoodCollect = this.get(Lists.newArrayList(Column.of("user_id", StpUtil.getLoginIdAsLong()), Column.of("good_id", goodId)));
        if (userGoodCollect == null) {
            userGoodCollect = new UserGoodCollect();
            userGoodCollect.setUserId(userId);
            userGoodCollect.setGoodId(goodId);
            this.save(userGoodCollect);
            return;
        }
        this.removeById(userGoodCollect.getId());
    }

    @Override
    public List<UserGoodResult> list(Long userId) {
        return ((UserGoodCollectMapper) super.getBaseMapper()).queryGoodByUserId(userId);
    }
}