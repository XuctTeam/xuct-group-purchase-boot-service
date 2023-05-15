/**
 * Copyright (C), 2015-2023, 263企业通信
 * FileName: LogInfoServiceImpl
 * Author:   Derek Xu
 * Date:     2023/5/12 17:55
 * Description:
 * History:
 * <author>          <time>          <version>          <desc>
 * 作者姓名           修改时间           版本号              描述
 */
package cn.com.xuct.group.purchase.service.impl;

import cn.com.xuct.group.purchase.base.enums.ColumnEnum;
import cn.com.xuct.group.purchase.base.enums.SortEnum;
import cn.com.xuct.group.purchase.base.service.BaseServiceImpl;
import cn.com.xuct.group.purchase.base.vo.Column;
import cn.com.xuct.group.purchase.base.vo.PageData;
import cn.com.xuct.group.purchase.base.vo.Sort;
import cn.com.xuct.group.purchase.entity.LogInfo;
import cn.com.xuct.group.purchase.entity.User;
import cn.com.xuct.group.purchase.mapper.LogInfoMapper;
import cn.com.xuct.group.purchase.service.LogInfoService;
import cn.hutool.core.date.DateTime;
import cn.hutool.core.date.DateUtil;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.google.common.collect.Lists;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;
import org.springframework.util.StringUtils;

import java.util.Date;
import java.util.List;

/**
 * 〈一句话功能简述〉<br>
 * 〈〉
 *
 * @author Derek Xu
 * @create 2023/5/12
 * @since 1.0.0
 */
@Service
public class LogInfoServiceImpl extends BaseServiceImpl<LogInfoMapper, LogInfo> implements LogInfoService {

    @Override
    public PageData<LogInfo> pagesList(final String module, final List<String> createTime, final Integer pageNo, final Integer pageSize) {
        List<Column> columnList = Lists.newArrayList();
        if (StringUtils.hasLength(module)) {
            columnList.add(Column.of("module", module, ColumnEnum.like));
        }
        if (!CollectionUtils.isEmpty(createTime)) {
            DateTime[] createTimes = {DateUtil.parseDate(createTime.get(0)), DateUtil.parseDate(createTime.get(1))};
            columnList.add(Column.of("create_time", createTimes, ColumnEnum.between));
        }
        return this.convert(this.pages(Page.of(pageNo, pageSize), columnList, Sort.of("id", SortEnum.asc)));
    }
}