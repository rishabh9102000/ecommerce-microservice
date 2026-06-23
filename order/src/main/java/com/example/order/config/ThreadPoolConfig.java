package com.example.order.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.concurrent.*;

@Configuration
public class ThreadPoolConfig {
    @Bean
    public ExecutorService orderProcessingExecutor() {
        return  new ThreadPoolExecutor(
                10,                              // corePoolSize - always-alive threads
                20,                              // maximumPoolSize - ceiling under load
                60L, TimeUnit.SECONDS,           // idle thread timeout beyond core size
                new LinkedBlockingQueue<>(50)    // bounded queue - backpressure
        );
    }
}
