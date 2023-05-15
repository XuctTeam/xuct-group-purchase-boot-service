/**
 * Copyright (C), 2015-2023, 263企业通信
 * FileName: DefaultProps
 * Author:   Derek Xu
 * Date:     2023/5/15 8:46
 * Description:
 * History:
 * <author>          <time>          <version>          <desc>
 * 作者姓名           修改时间           版本号              描述
 */
package cn.com.xuct.group.purchase.config;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

/**
 * 〈一句话功能简述〉<br> 
 * 〈〉
 *
 * @author Derek Xu
 * @create 2023/5/15
 * @since 1.0.0
 */
@Data
@Configuration
@ConfigurationProperties(prefix = "default.props")
public class DefaultProperties {


    private String userPassword;
}