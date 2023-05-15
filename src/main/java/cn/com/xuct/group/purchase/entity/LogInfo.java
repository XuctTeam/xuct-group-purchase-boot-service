/**
 * Copyright (C), 2015-2023, 263企业通信
 * FileName: LogInfo
 * Author:   Derek Xu
 * Date:     2023/5/12 17:44
 * Description:
 * History:
 * <author>          <time>          <version>          <desc>
 * 作者姓名           修改时间           版本号              描述
 */
package cn.com.xuct.group.purchase.entity;

import cn.com.xuct.group.purchase.base.dao.SuperEntity;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.databind.ser.std.ToStringSerializer;
import lombok.*;

/**
 * 〈一句话功能简述〉<br> 
 * 〈〉
 *
 * @author Derek Xu
 * @create 2023/5/12
 * @since 1.0.0
 */
@EqualsAndHashCode(callSuper = true)
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@TableName(value = "sys_log_info")
public class LogInfo extends SuperEntity<LogInfo> {

    // 功能模块
    private String module;

    // 操作类型
    private String type;

    // 操作描述
    private String message;

    // 请求参数

    private String reqParam;

    // 响应参数
    private String resParam;

    // 耗时
    @JsonSerialize(using = ToStringSerializer.class)
    private Long takeUpTime;

    // 操作用户id
    private Long userId;

    // 操作用户名称
    private String userName;

    // 操作方法
    private String method;

    // 请求url
    private String uri;

    // 请求IP
    private String ip;

    // 版本号
    private String version;
}