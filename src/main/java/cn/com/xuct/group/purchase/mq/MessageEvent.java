/**
 * Copyright (C), 2015-2023, 263企业通信
 * FileName: MessageEvent
 * Author:   Derek Xu
 * Date:     2023/5/17 8:33
 * Description:
 * History:
 * <author>          <time>          <version>          <desc>
 * 作者姓名           修改时间           版本号              描述
 */
package cn.com.xuct.group.purchase.mq;

import cn.com.xuct.group.purchase.constants.EventCodeEnum;
import lombok.Getter;
import lombok.Setter;
import org.springframework.context.ApplicationEvent;

import java.time.Clock;

/**
 * 〈一句话功能简述〉<br> 
 * 〈〉
 *
 * @author Derek Xu
 * @create 2023/5/17
 * @since 1.0.0
 */
@Setter
@Getter
public class MessageEvent<T> extends ApplicationEvent {

    private EventCodeEnum code;

    private T data;


    public MessageEvent(Object source, EventCodeEnum code, T data) {
        super(source);
        this.code = code;
        this.data = data;
    }
}