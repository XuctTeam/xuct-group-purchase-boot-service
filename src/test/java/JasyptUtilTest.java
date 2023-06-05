/**
 * Copyright (C), 2015-2023, 263企业通信
 * FileName: JasyptUtilTest
 * Author:   Derek Xu
 * Date:     2023/4/18 18:07
 * Description:
 * History:
 * <author>          <time>          <version>          <desc>
 * 作者姓名           修改时间           版本号              描述
 */


import cn.com.xuct.group.purchase.GroupPurchaseApplication;
import org.jasypt.encryption.StringEncryptor;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
/**
 * 〈一句话功能简述〉<br> 
 * 〈〉
 *
 * @author Derek Xu
 * @create 2023/4/18
 * @since 1.0.0
 */

@SpringBootTest(classes = GroupPurchaseApplication.class)
public class JasyptUtilTest {

    @Autowired
    private StringEncryptor encryptor;

    @Test
    void contextLoads() {
        String test = encryptor.encrypt("123456");
        System.out.println(test);
    }
}