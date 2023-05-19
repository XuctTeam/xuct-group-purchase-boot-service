/**
 * Copyright (C), 2015-2023, 263企业通信
 * FileName: IAdminMenuConvert
 * Author:   Derek Xu
 * Date:     2023/5/19 9:32
 * Description:
 * History:
 * <author>          <time>          <version>          <desc>
 * 作者姓名           修改时间           版本号              描述
 */
package cn.com.xuct.group.purchase.mapstruct;

import cn.com.xuct.group.purchase.entity.Resource;
import cn.com.xuct.group.purchase.vo.result.admin.AdminMenuResult;
import cn.com.xuct.group.purchase.vo.result.admin.AdminMenuTreeResult;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;

/**
 * 〈一句话功能简述〉<br>
 * 〈〉
 *
 * @author Derek Xu
 * @create 2023/5/19
 * @since 1.0.0
 */
@Mapper
public interface IAdminMenuConvert {

    IAdminMenuConvert INSTANCE = Mappers.getMapper(IAdminMenuConvert.class);


    @Mapping(target = "name", source = "pathName")
    @Mapping(target = "meta.icon", source = "icon")
    @Mapping(target = "meta.title", source = "title")
    @Mapping(target = "meta.link", source = "link")
    @Mapping(target = "meta.hide", source = "hide")
    @Mapping(target = "meta.full", source = "full")
    @Mapping(target = "meta.affix", source = "affix")
    @Mapping(target = "meta.keepAlive", source = "keepAlive")
    AdminMenuResult resource2Menu(Resource resource);


    @Mapping(target = "label", source = "title")
    @Mapping(target = "value", source = "id")
    AdminMenuTreeResult resource2TreeMenu(Resource resource);

}