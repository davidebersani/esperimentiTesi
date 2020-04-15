package esperimenti.templateservice.aoplog;

import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.*;
import org.springframework.stereotype.Component;

import java.util.Arrays;

@Component
@Aspect
@Slf4j
public class ActionLoggingAspect {

    private static void logWithType(JoinPoint joinPoint, LoggingType loggingType) {
        String context = "ACTION";
        final String args = Arrays.toString(joinPoint.getArgs());
        final String methodName = joinPoint.getSignature().toShortString().replace("(..)", "()");
        log.info("{} context:{} [method:{}, args:{}]", loggingType, context, methodName, args);
    }

    // Pointcut che rappresenta i metodi della classe TemplateService
    @Pointcut("execution(* esperimenti.templateservice.service.stringconsumers.*.*(..))")
    public void templateCalls() {}

    // Eseguito prima dell'esecuzione del metodo
    @Before("templateCalls()")
    public void logBeforeExecuteMethod(JoinPoint joinPoint) {
        logWithType(joinPoint, LoggingType.REQUEST);
    }

    // Eseguito quando il metodo è terminato (con successo)
    @AfterReturning(value = "templateCalls()")
    public void logSuccessMethod(JoinPoint joinPoint) {
        logWithType(joinPoint,LoggingType.SUCCESS);
    }

    // Eseguito se è stata sollevata un'eccezione
    @AfterThrowing("templateCalls()")
    public void logErrorApplication(JoinPoint joinPoint) {
        logWithType(joinPoint,LoggingType.FAIL);
    }
}
