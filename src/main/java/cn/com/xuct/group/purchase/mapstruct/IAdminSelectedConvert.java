/**
 * Copyright (C), 2015-2023, 263企业通信
 * FileName: IWaresSelectedConvert
 * Author:   Derek Xu
 * Date:     2023/5/19 9:26
 * Description:
 * History:
 * <author>          <time>          <version>          <desc>
 * 作者姓名           修改时间           版本号              描述
 */
package cn.com.xuct.group.purchase.mapstruct;

import cn.com.xuct.group.purchase.entity.Role;
import cn.com.xuct.group.purchase.entity.Wares;
import cn.com.xuct.group.purchase.vo.result.admin.AdminSelectedResult;
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
public interface IAdminSelectedConvert {

    IAdminSelectedConvert INSTANCE = Mappers.getMapper(IAdminSelectedConvert.class);


    @Mapping(source = "id", target = "value")
    @Mapping(source = "name", target = "label")
    AdminSelectedResult waresToSelected(Wares wares);

    @Mapping(source = "id", target = "value")
    @Mapping(source = "name", target = "label")
    AdminSelectedResult roleToSelected(Role role);

}