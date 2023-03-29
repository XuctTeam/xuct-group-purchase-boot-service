/**
 * Copyright (C), 2015-2023, XXX有限公司
 * FileName: UserCartServiceImpl
 * Author:   Derek Xu
 * Date:     2023/3/29 10:10
 * Description:
 * History:
 * <author>          <time>          <version>          <desc>
 * Derek Xu         修改时间           版本号              描述
 */
package cn.com.xuct.group.purchase.service.impl;

import cn.com.xuct.group.purchase.base.service.BaseServiceImpl;
import cn.com.xuct.group.purchase.entity.UserGoodCart;
import cn.com.xuct.group.purchase.mapper.UserGoodCartMapper;
import cn.com.xuct.group.purchase.service.UserGoodCartService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

/**
 * 〈一句话功能简述〉<br> 
 * 〈〉
 *
 * @author Derek Xu
 * @create 2023/3/29
 * @since 1.0.0
 */
@Slf4j
@Service
public class UserGoodCartServiceImpl extends BaseServiceImpl<UserGoodCartMapper, UserGoodCart> implements UserGoodCartService {


    @Override
    public void addCart(Long gid, Long uid) {
        ((UserGoodCartMapper)super.getBaseMapper()).addGoodCart(gid , uid);
    }
}