/**
 * Copyright (C), 2015-2023, 263企业通信
 * FileName: AppConfigServiceImpl
 * Author:   Derek Xu
 * Date:     2023/4/10 11:20
 * Description:
 * History:
 * <author>          <time>          <version>          <desc>
 * 作者姓名           修改时间           版本号              描述
 */
package cn.com.xuct.group.purchase.service.impl;

import cn.com.xuct.group.purchase.base.service.BaseServiceImpl;
import cn.com.xuct.group.purchase.base.vo.Column;
import cn.com.xuct.group.purchase.constants.RedisCacheConstants;
import cn.com.xuct.group.purchase.entity.AppConfig;
import cn.com.xuct.group.purchase.mapper.AppConfigMapper;
import cn.com.xuct.group.purchase.service.AppConfigService;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

/**
 * 〈一句话功能简述〉<br>
 * 〈〉
 *
 * @author Derek Xu
 * @create 2023/4/10
 * @since 1.0.0
 */
@Service
public class AppConfigServiceImpl extends BaseServiceImpl<AppConfigMapper, AppConfig> implements AppConfigService {

    @Override
    @Cacheable(cacheNames = RedisCacheConstants.APP_CACHE_CONFIG_NAME, key = "#type", unless = "#result == null")
    public AppConfig get(Integer type) {
        return this.get(Column.of("type", type));
    }
}