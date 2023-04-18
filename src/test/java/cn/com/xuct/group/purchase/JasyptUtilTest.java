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
package cn.com.xuct.group.purchase;



import org.jasypt.encryption.StringEncryptor;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
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

@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest(classes = GroupPurchaseApplication.class)
public class JasyptUtilTest {

    @Autowired
    private StringEncryptor encryptor;

    @Test
    public void jasypt(){
        String name = encryptor.encrypt("hello");
        System.out.println("en: " + name);
        System.out.println("de: " + encryptor.decrypt(name));
    }

}