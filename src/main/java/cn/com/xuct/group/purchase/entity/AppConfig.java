/**
 * Copyright (C), 2015-2023, 263企业通信
 * FileName: PrivacyAgreement
 * Author:   Derek Xu
 * Date:     2023/4/10 11:16
 * Description:
 * History:
 * <author>          <time>          <version>          <desc>
 * 作者姓名           修改时间           版本号              描述
 */
package cn.com.xuct.group.purchase.entity;

import cn.com.xuct.group.purchase.base.dao.SuperEntity;
import com.baomidou.mybatisplus.annotation.TableName;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * 〈一句话功能简述〉<br> 
 * 〈〉
 *
 * @author Derek Xu
 * @create 2023/4/10
 * @since 1.0.0
 */
@EqualsAndHashCode(callSuper = true)
@Data
@TableName("sys_app_config")
public class AppConfig extends SuperEntity<AppConfig> {

    @Schema(description = "类型 0 隐私协议 1用户协议 2 版本号" )
    public Integer type;

    @Schema(description = "内容")
    private String content;
}