/**
 * Copyright (C), 2015-2023, 263企业通信
 * FileName: ApplicationRunner
 * Author:   Derek Xu
 * Date:     2023/4/18 15:47
 * Description:
 * History:
 * <author>          <time>          <version>          <desc>
 * 作者姓名           修改时间           版本号              描述
 */
package cn.com.xuct.group.purchase.config;

import cn.com.xuct.group.purchase.client.cos.client.CosClient;
import org.springframework.beans.factory.DisposableBean;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.stereotype.Component;

/**
 * 〈一句话功能简述〉<br>
 * 〈〉
 *
 * @author Derek Xu
 * @create 2023/4/18
 * @since 1.0.0
 */
@Component
public class InitApplicationRunner implements ApplicationRunner, DisposableBean {

    @Override
    public void run(ApplicationArguments args) {
        CosClient.initCosClient();
    }

    @Override
    public void destroy() {
        CosClient.shutdownClient();
    }
}