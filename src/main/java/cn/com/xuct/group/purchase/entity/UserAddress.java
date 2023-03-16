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

import com.baomidou.mybatisplus.annotation.TableName;
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
public class UserAddress {

    /**
     * 用户ID
     */
    private Long userId;


    /**
     * 详细地址
     */
    private String address;

    /**
     * 是否首选 0不是 1是
     */
    private Integer firstChoose;
}