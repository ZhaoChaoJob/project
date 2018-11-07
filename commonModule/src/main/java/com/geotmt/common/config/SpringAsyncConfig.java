package com.geotmt.common.config;
import org.springframework.context.annotation.Bean;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;

import java.util.concurrent.Executor;

/**
 * 异步配置
 *
 * Created by chao.zhao on 2018/3/13. */
@EnableAsync
public class SpringAsyncConfig {
    private int corePoolSize = 10;//线程池维护线程的最少数量
    private int maxPoolSize = 200;//线程池维护线程的最大数量
    private int queueCapacity = 10;//线程池所使用的缓冲队列

    /**
     * ispSpiderExecutor，用着爬虫二次请求
     *
     * @return ispSpider线程池
     */
    @Bean
    public Executor ispSpider() {
        ThreadPoolTaskExecutor executor = new ThreadPoolTaskExecutor();
        executor.setCorePoolSize(corePoolSize);
        executor.setMaxPoolSize(maxPoolSize);
        executor.setQueueCapacity(queueCapacity);
        executor.setThreadNamePrefix("zwxExecutor-");
        executor.setKeepAliveSeconds(5);//超过core size的那些线程，任务完成后，再经过这个时长（秒）会被结束掉
        // 设置拒绝策略
        executor.setRejectedExecutionHandler((r, executor1) -> {
            // TODO 添加报警
        });
        executor.initialize();
        return executor;
    }

    /**
     * listenableFutureExecutor，用于监听ispSpider线程
     * @return listenableFuture线程池
     */
    @Bean
    public Executor listenableFuture() {
        ThreadPoolTaskExecutor executor = new ThreadPoolTaskExecutor();
        executor.setCorePoolSize(corePoolSize);
        executor.setMaxPoolSize(maxPoolSize);
        executor.setQueueCapacity(queueCapacity);
        executor.setThreadNamePrefix("zwxFuture-");
        executor.setKeepAliveSeconds(5);//超过core size的那些线程，任务完成后，再经过这个时长（秒）会被结束掉
        executor.initialize();
        return executor;
    }
}

