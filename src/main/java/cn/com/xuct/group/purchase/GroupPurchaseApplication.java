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

        SpringApplication.run(GroupPurchaseApplication.class, args);
    }

}