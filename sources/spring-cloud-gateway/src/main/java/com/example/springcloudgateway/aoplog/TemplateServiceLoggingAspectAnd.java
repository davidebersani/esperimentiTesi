package com.example.springcloudgateway.aoplog;

import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.stereotype.Component;

@Component
@Aspect
public class TemplateServiceLoggingAspectAnd extends BeforeAndAfterLogging {

    @Override
    public String getContext() {
        return "SERVICE";
    }

    @Override
    @Pointcut("execution(* com.example.springcloudgateway.service.TemplateService.*(..))")
    public void getPointcut() {

    }
}
