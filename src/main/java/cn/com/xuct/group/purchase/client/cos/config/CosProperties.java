/**
 * Copyright (C), 2015-2023, 263企业通信
 * FileName: CosProperties
 * Author:   Derek Xu
 * Date:     2023/4/18 15:23
 * Description:
 * History:
 * <author>          <time>          <version>          <desc>
 * 作者姓名           修改时间           版本号              描述
 */
package cn.com.xuct.group.purchase.client.cos.config;

import lombok.Data;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

/**
 * 〈一句话功能简述〉<br>
 * 〈〉
 *
 * @author Derek Xu
 * @create 2023/4/18
 * @since 1.0.0
 */
@Slf4j
@Setter
@Configuration
@ConfigurationProperties(prefix = "cos")
public class CosProperties implements InitializingBean {


    private String secretId;
    private String secretKey;
    private String bucketName;
    private String region;

    public static String Tencent_secretId;
    public static String Tencent_secretKey;
    public static String Tencent_bucketName;
    public static String Tencent_region;

    @Override
    public void afterPropertiesSet() {
        Tencent_secretId = secretId;
        Tencent_secretKey = secretKey;
        Tencent_bucketName = bucketName;
        Tencent_region = region;
        log.info("密钥初始化成功");
    }
}