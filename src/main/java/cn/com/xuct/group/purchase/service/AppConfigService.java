/**
 * Copyright (C), 2015-2023, 263企业通信
 * FileName: AppConfigService
 * Author:   Derek Xu
 * Date:     2023/4/10 11:20
 * Description:
 * History:
 * <author>          <time>          <version>          <desc>
 * 作者姓名           修改时间           版本号              描述
 */
package cn.com.xuct.group.purchase.service;

import cn.com.xuct.group.purchase.base.service.IBaseService;
import cn.com.xuct.group.purchase.entity.AppConfig;
import cn.com.xuct.group.purchase.mapper.AppConfigMapper;

/**
 * 〈一句话功能简述〉<br> 
 * 〈〉
 *
 * @author Derek Xu
 * @create 2023/4/10
 * @since 1.0.0
 */
public interface AppConfigService extends IBaseService<AppConfigMapper , AppConfig> {


    AppConfig get(final Integer type);

    /**
     *
     * @param type
     * @return
     */
    AppConfig saveAppConfig(final AppConfig appConfig);
}