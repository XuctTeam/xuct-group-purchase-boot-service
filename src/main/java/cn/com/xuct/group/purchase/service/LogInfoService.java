/**
 * Copyright (C), 2015-2023, 263企业通信
 * FileName: LogInfoService
 * Author:   Derek Xu
 * Date:     2023/5/12 17:54
 * Description:
 * History:
 * <author>          <time>          <version>          <desc>
 * 作者姓名           修改时间           版本号              描述
 */
package cn.com.xuct.group.purchase.service;

import cn.com.xuct.group.purchase.base.service.IBaseService;
import cn.com.xuct.group.purchase.base.vo.PageData;
import cn.com.xuct.group.purchase.entity.LogInfo;
import cn.com.xuct.group.purchase.mapper.LogInfoMapper;

import java.util.List;

/**
 * 〈一句话功能简述〉<br>
 * 〈〉
 *
 * @author Derek Xu
 * @create 2023/5/12
 * @since 1.0.0
 */
public interface LogInfoService extends IBaseService<LogInfoMapper, LogInfo> {

    /**
     * 分页查询日志
     *
     * @param module
     * @param createTime
     * @param pageNo
     * @param pageSize
     * @return
     */
    PageData<LogInfo> pagesList(final String module, final List<String> createTime, final Integer pageNo, final Integer pageSize);
}