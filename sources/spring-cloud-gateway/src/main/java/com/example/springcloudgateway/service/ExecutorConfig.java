package com.example.springcloudgateway.service;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.context.annotation.RequestScope;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

@Configuration
public class ExecutorConfig {

    @Bean
    @RequestScope
    public ExecutorService getExecutor() {
        return Executors.newCachedThreadPool();
    }
}
