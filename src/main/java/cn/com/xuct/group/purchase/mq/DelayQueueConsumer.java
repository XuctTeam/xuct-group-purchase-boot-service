/**
 * Copyright (C), 2015-2023, 263企业通信
 * FileName: DelayQueueConsumer
 * Author:   Derek Xu
 * Date:     2023/5/16 14:19
 * Description:
 * History:
 * <author>          <time>          <version>          <desc>
 * 作者姓名           修改时间           版本号              描述
 */
package cn.com.xuct.group.purchase.mq;

import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;
import java.util.Date;
import static cn.com.xuct.group.purchase.config.DelayedQueueConfiguration.DELAYED_QUEUE_NAME;

/**
 * 〈一句话功能简述〉<br>
 * 〈〉
 *
 * @author Derek Xu
 * @create 2023/5/16
 * @since 1.0.0
 */
@Slf4j
@Component
public class DelayQueueConsumer {

    @RabbitListener(queues = DELAYED_QUEUE_NAME)
    public void receiveDelayedQueue(Message message) {
        String msg = new String(message.getBody());
        log.info("当前时间：{},收到延时队列的消息：{}", new Date().toString(), msg);
    }
}