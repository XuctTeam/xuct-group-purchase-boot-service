/**
 * Copyright (C), 2015-2023, 263企业通信
 * FileName: IMqDelayedConvert
 * Author:   Derek Xu
 * Date:     2023/5/19 10:23
 * Description:
 * History:
 * <author>          <time>          <version>          <desc>
 * 作者姓名           修改时间           版本号              描述
 */
package cn.com.xuct.group.purchase.mapstruct;

import cn.com.xuct.group.purchase.entity.Coupon;
import cn.com.xuct.group.purchase.entity.Wares;
import cn.com.xuct.group.purchase.vo.dto.CouponDelayedDto;
import cn.com.xuct.group.purchase.vo.dto.WaresDelayedDto;
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
public interface IDelayedConvert {

    IDelayedConvert INSTANCE = Mappers.getMapper(IDelayedConvert.class);

    @Mapping(source = "id", target = "waresId")
    WaresDelayedDto wares2Delayed(Wares wares);


    @Mapping(source = "id", target = "couponId")
    CouponDelayedDto coupon2Delayed(Coupon coupon);
}