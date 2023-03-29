/**
 * Copyright (C), 2015-2023, XXX有限公司
 * FileName: UserAddress
 * Author:   Derek Xu
 * Date:     2023/3/16 13:26
 * Description:
 * History:
 * <author>          <time>          <version>          <desc>
 * Derek Xu         修改时间           版本号              描述
 */
package cn.com.xuct.group.purchase.entity;

import cn.com.xuct.group.purchase.base.dao.SuperEntity;
import com.baomidou.mybatisplus.annotation.TableName;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.databind.ser.std.ToStringSerializer;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

/**
 * 〈一句话功能简述〉<br> 
 * 〈〉
 *
 * @author Derek Xu
 * @create 2023/3/16
 * @since 1.0.0
 */
@Data
@TableName("sys_user_address")
public class UserAddress extends SuperEntity<UserAddress> {

    @Schema(description = "用户ID")
    @JsonSerialize(using = ToStringSerializer.class)
    private Long userId;

    @Schema(description = "省")
    private String provinceName;


    @Schema(description = "市")
    private String cityName;


    @Schema(description = "县区")
    private String countyName;


    @Schema(description = "电话")
    private String telNumber;


    @Schema(description = "详细地址")
    private String detailInfo;

    @Schema(description = "姓名")
    private String userName;

    @Schema(description = "是否首选 0不是 1是")
    private Integer firstChoose;
}