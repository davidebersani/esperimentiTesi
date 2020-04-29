package com.example.springcloudgateway.aoplog;

import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.stereotype.Component;

@Component
@Aspect

public class MsgAdaptersLoggingAspectAnd extends BeforeAndAfterLogging {

    @Override
    public String getContext() {
        return "MSG";
    }

    @Override
    @Pointcut("execution(* com.example.springcloudgateway.msgAdapters.*.*(..))")
    public void getPointcut() {

    }
}
