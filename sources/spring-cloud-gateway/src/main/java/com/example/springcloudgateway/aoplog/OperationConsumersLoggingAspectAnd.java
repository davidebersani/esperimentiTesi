package com.example.springcloudgateway.aoplog;

import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.stereotype.Component;

@Component
@Aspect
public class OperationConsumersLoggingAspectAnd extends BeforeAndAfterLogging {

    @Override
    public String getContext() {
        return "CONSUMER";
    }

    @Override
    @Pointcut("within(com.example.springcloudgateway.operationsParsers..*)")
    public void getPointcut() {

    }
}
