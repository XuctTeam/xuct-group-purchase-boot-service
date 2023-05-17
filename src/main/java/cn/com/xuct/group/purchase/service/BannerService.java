/**
 * Copyright (C), 2015-2023, XXX有限公司
 * FileName: BannerService
 * Author:   Derek Xu
 * Date:     2023/3/22 10:46
 * Description:
 * History:
 * <author>          <time>          <version>          <desc>
 * Derek Xu         修改时间           版本号              描述
 */
package cn.com.xuct.group.purchase.service;

import cn.com.xuct.group.purchase.base.service.IBaseService;
import cn.com.xuct.group.purchase.entity.Banner;
import cn.com.xuct.group.purchase.mapper.BannerMapper;

import java.util.List;

/**
 * 〈一句话功能简述〉<br>
 * 〈〉
 *
 * @author Derek Xu
 * @create 2023/3/22
 * @since 1.0.0
 */
public interface BannerService extends IBaseService<BannerMapper, Banner> {


    /**
     * 查询列表
     * @param title
     * @param status
     * @return
     */
    List<Banner> list(final String title , final Integer status);

    /**
     * 修改轮播图状态
     *
     * @param bannerId
     * @param status
     * @return
     */
    boolean changeBannerStatus(final Long bannerId, final Integer status);
}