/**
 * Copyright (C), 2015-2023, 263企业通信
 * FileName: ThreadPoolConfiguration
 * Author:   Derek Xu
 * Date:     2023/6/7 9:07
 * Description:
 * History:
 * <author>          <time>          <version>          <desc>
 * 作者姓名           修改时间           版本号              描述
 */
package cn.com.xuct.group.purchase.config;

import lombok.extern.slf4j.Slf4j;
import org.jetbrains.annotations.NotNull;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.task.TaskDecorator;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;
import org.springframework.web.context.request.RequestAttributes;
import org.springframework.web.context.request.RequestContextHolder;

import java.util.concurrent.Executor;
import java.util.concurrent.ThreadPoolExecutor;

/**
 * 〈一句话功能简述〉<br>
 * 〈〉
 *
 * @author Derek Xu
 * @create 2023/6/7
 * @since 1.0.0
 */
@Slf4j
@Configuration
@EnableAsync // 启动异步调用
public class ThreadPoolConfiguration {

    @Value("${executor.core-pool-size}")
    private Integer corePoolSize = 10;

    @Value("${executor.max-pool-size}")
    private Integer maxPoolSize = 30;

    @Value("${executor.queue-capacity}")
    private Integer queueCapacity = 5;


    private final String TASK_EXECUTOR_NAME  = "threadExecutor";


    @Bean(TASK_EXECUTOR_NAME)
    public Executor getAsyncExecutor() {

        ThreadPoolTaskExecutor executor = new ThreadPoolTaskExecutor(); //不调用重写的线程池
        //配置核心线程数
        executor.setCorePoolSize(corePoolSize);
        //配置最大线程数
        executor.setMaxPoolSize(maxPoolSize);
        //配置队列大小
        executor.setQueueCapacity(queueCapacity);
        //配置线程池的线程的名称前缀 如果不定义，则使用系统默认的线程池
        executor.setThreadNamePrefix("async-testThread-");
        //设置拒绝策略
        executor.setRejectedExecutionHandler(new ThreadPoolExecutor.CallerRunsPolicy());
        //设置线程的空闲时间，达到了就删除这个线程
        executor.setKeepAliveSeconds(10);
        // 增加 TaskDecorator 属性的配置
        executor.setTaskDecorator(new ContextDecorator());
        //执行初始化
        executor.initialize();
        log.info("线程池初始化....");
        return executor;
    }

    /**
     * 任务装饰器
     */
    static class ContextDecorator implements TaskDecorator {
        @NotNull
        @Override
        public Runnable decorate(@NotNull Runnable runnable) {
            RequestAttributes context = RequestContextHolder.currentRequestAttributes();
            return () -> {
                try {
                    // 传递上下文
                    RequestContextHolder.setRequestAttributes(context);
                    runnable.run();
                } finally {
                    RequestContextHolder.resetRequestAttributes();
                }
            };
        }
    }
}