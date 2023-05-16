/**
 * Copyright (C), 2015-2023, 263企业通信
 * FileName: MyMetaObjectHandler
 * Author:   Derek Xu
 * Date:     2023/5/16 14:54
 * Description:
 * History:
 * <author>          <time>          <version>          <desc>
 * 作者姓名           修改时间           版本号              描述
 */
package cn.com.xuct.group.purchase.handler;

import com.baomidou.mybatisplus.core.handlers.MetaObjectHandler;
import org.apache.ibatis.reflection.MetaObject;
import org.springframework.stereotype.Component;

import java.util.Date;

/**
 * 〈一句话功能简述〉<br>
 * 〈〉
 *
 * @author Derek Xu
 * @create 2023/5/16
 * @since 1.0.0
 */
@Component
public class MyMetaObjectHandler implements MetaObjectHandler {

    @Override
    public void insertFill(MetaObject metaObject) {
        this.setFieldValByName("create_time", new Date(), metaObject);
        this.setFieldValByName("update_time", new Date(), metaObject);
        //添加数据时，version默认填充为1
        this.setFieldValByName("version",1,metaObject);
    }

    //mp执行更新操作时，执行此操作
    @Override
    public void updateFill(MetaObject metaObject) {
        this.setFieldValByName("update_time", new Date(), metaObject);
    }
}