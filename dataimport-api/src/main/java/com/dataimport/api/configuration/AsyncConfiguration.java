package com.dataimport.api.configuration;

import java.util.concurrent.ForkJoinPool;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.EnableAsync;

@Configuration
@EnableAsync
public class AsyncConfiguration {

    @Bean
    public ForkJoinPool forkJoinPool() {
        return new ForkJoinPool(6,
                ForkJoinPool.defaultForkJoinWorkerThreadFactory, null, true);
    }
}
