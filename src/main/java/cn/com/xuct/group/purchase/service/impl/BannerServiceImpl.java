/**
 * Copyright (C), 2015-2023, XXX有限公司
 * FileName: BannerServiceImpl
 * Author:   Derek Xu
 * Date:     2023/3/22 10:47
 * Description:
 * History:
 * <author>          <time>          <version>          <desc>
 * Derek Xu         修改时间           版本号              描述
 */
package cn.com.xuct.group.purchase.service.impl;

import cn.com.xuct.group.purchase.base.enums.ColumnEnum;
import cn.com.xuct.group.purchase.base.service.BaseServiceImpl;
import cn.com.xuct.group.purchase.base.vo.Column;
import cn.com.xuct.group.purchase.entity.Banner;
import cn.com.xuct.group.purchase.mapper.BannerMapper;
import cn.com.xuct.group.purchase.service.BannerService;
import com.google.common.collect.Lists;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.util.List;

/**
 * 〈一句话功能简述〉<br> 
 * 〈〉
 *
 * @author Derek Xu
 * @create 2023/3/22
 * @since 1.0.0
 */
@Service
public class BannerServiceImpl extends BaseServiceImpl<BannerMapper, Banner> implements BannerService {

    @Override
    public List<Banner> list(String title, Integer status) {
        List<Column> columnList = Lists.newArrayList();
        if(StringUtils.hasLength(title)){
            columnList.add(Column.of("title" , title , ColumnEnum.like));
        }
        if(status != null){
            columnList.add(Column.of("status" , status));
        }
        return this.find(columnList);
    }

    @Override
    public boolean changeBannerStatus(Long bannerId, Integer status) {
        Banner banner = this.getById(bannerId);
        if(banner == null){
            return false;
        }
        banner.setStatus(status);
        this.updateById(banner);
        return true;
    }
}