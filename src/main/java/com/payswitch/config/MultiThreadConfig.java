/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.payswitch.config;

import java.util.concurrent.Executor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;

/**
 *
 * @author Dell
 */
@Configuration
public class MultiThreadConfig {

    @Bean
    public Executor taskExecutor() {
        ThreadPoolTaskExecutor executor = new ThreadPoolTaskExecutor();
        executor.setCorePoolSize(ParamsConfig.ADAPTER_CORE_POOLING_SIZE);
        executor.setMaxPoolSize(ParamsConfig.ADAPTER_MAX_POOLING_SIZE);
        executor.setQueueCapacity(ParamsConfig.ADAPTER_QUEUE_CAPACITY);
        executor.setThreadNamePrefix(ParamsConfig.ADAPTER_DEFAULT_PREFIX);
        executor.initialize();
        return executor;
    }

}
