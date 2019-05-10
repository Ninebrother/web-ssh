package com.alon.ssh.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;
import org.yeauty.standard.ServerEndpointExporter;
/**
 * Created by zhangyl on 2019/4/4
 */
@Configuration
public class WebSocketConfig {
    @Bean
    public ServerEndpointExporter serverEndpointExporter() {
        return new ServerEndpointExporter();
    }

    @Bean
    public ThreadPoolTaskExecutor executor() {
        ThreadPoolTaskExecutor poolTaskExecutor = new ThreadPoolTaskExecutor();
        poolTaskExecutor.setMaxPoolSize(20);
        poolTaskExecutor.setQueueCapacity(0);
        return poolTaskExecutor;
    }
}