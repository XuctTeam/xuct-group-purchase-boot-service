/**
 * Copyright (C), 2015-2023, XXX有限公司
 * FileName: GroupPurchaseApplication
 * Author:   Derek Xu
 * Date:     2023/3/16 9:35
 * Description:
 * History:
 * <author>          <time>          <version>          <desc>
 * Derek Xu         修改时间           版本号              描述
 */
package cn.com.xuct.group.purchase;

import com.ulisesbocchio.jasyptspringboot.encryptor.DefaultLazyEncryptor;
import org.jasypt.encryption.pbe.StandardPBEStringEncryptor;
import org.jasypt.encryption.pbe.config.EnvironmentStringPBEConfig;
import org.jasypt.util.password.BasicPasswordEncryptor;
import org.jasypt.util.text.BasicTextEncryptor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.transaction.annotation.EnableTransactionManagement;

/**
 * 〈一句话功能简述〉<br>
 * 〈〉
 *
 * @author Derek Xu
 * @create 2023/3/16
 * @since 1.0.0
 */
@SpringBootApplication
@EnableCaching
@EnableTransactionManagement
public class GroupPurchaseApplication {


    public static void main(String[] args) {

//        StandardPBEStringEncryptor encryptor = new StandardPBEStringEncryptor();
//
//        EnvironmentStringPBEConfig pconf = new EnvironmentStringPBEConfig();
//        pconf.setAlgorithm("PBEWithMD5AndDES");
//        pconf.setPassword("xutao_cn_com_xuct");
//        encryptor.setConfig(pconf);
//        System.out.println(encryptor.encrypt("YtNdeR0LRIvLYXH9mBLHzShiW7VggXAZ"));
        SpringApplication.run(GroupPurchaseApplication.class, args);
    }


}