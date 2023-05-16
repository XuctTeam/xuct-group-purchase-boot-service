/**
 * Copyright (C), 2015-2023, 263企业通信
 * FileName: DelayedQueueConfiguration
 * Author:   Derek Xu
 * Date:     2023/5/16 14:16
 * Description:
 * History:
 * <author>          <time>          <version>          <desc>
 * 作者姓名           修改时间           版本号              描述
 */
package cn.com.xuct.group.purchase.config;

import org.springframework.amqp.core.Binding;
import org.springframework.amqp.core.BindingBuilder;
import org.springframework.amqp.core.CustomExchange;
import org.springframework.amqp.core.Queue;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.HashMap;
import java.util.Map;

/**
 * 〈一句话功能简述〉<br>
 * 〈〉
 *
 * @author Derek Xu
 * @create 2023/5/16
 * @since 1.0.0
 */
@Configuration
public class DelayedQueueConfiguration {


    //延迟队列、交换机及路由名称
    public static final String DELAYED_QUEUE_NAME = "group.delayed.queue";
    public static final String DELAYED_EXCHANGE_NAME = "group.delayed.exchange";
    public static final String DELAYED_ROUTING_KEY = "group.delayed.routingkey";


    @Bean("delayedQueue")
    public Queue delayedQueue() {
        return new Queue(DELAYED_QUEUE_NAME);
    }

    //自定义交换机 我们在这里定义的是一个延迟交换机
    @Bean("delayedExchange")
    public CustomExchange delayedExchange() {
        Map<String, Object> args = new HashMap<>();
        //自定义交换机的类型，指定分发方式
        args.put("x-delayed-type", "direct");
        //此处type指定为延迟交换机
        return new CustomExchange(DELAYED_EXCHANGE_NAME, "x-delayed-message", true, false, args);
    }

    @Bean
    public Binding bindingDelayedQueue(@Qualifier("delayedQueue") Queue queue,
                                       @Qualifier("delayedExchange") CustomExchange delayedExchange) {
        return BindingBuilder.bind(queue).to(delayedExchange).with(DELAYED_ROUTING_KEY).noargs();
    }
}