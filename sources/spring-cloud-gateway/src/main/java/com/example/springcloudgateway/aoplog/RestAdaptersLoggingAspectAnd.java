package com.example.springcloudgateway.aoplog;

import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.stereotype.Component;


@Component
@Aspect

public class RestAdaptersLoggingAspectAnd extends BeforeAndAfterLogging {


    @Override
    public String getContext() {
        return "REST";
    }

    @Override
    @Pointcut("execution(* com.example.springcloudgateway.restAdapters.*.*(..))")
    public void getPointcut() {

    }
}
