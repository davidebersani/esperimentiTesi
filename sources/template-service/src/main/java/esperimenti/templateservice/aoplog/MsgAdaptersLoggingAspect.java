package esperimenti.templateservice.aoplog;

import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.*;
import org.springframework.stereotype.Component;

import java.util.Arrays;

@Component
@Aspect
@Slf4j
public class MsgAdaptersLoggingAspect {

    private static void logWithType(JoinPoint joinPoint, LoggingType loggingType) {
        String context = "MSG";
        final String args = Arrays.toString(joinPoint.getArgs());
        final String methodName = joinPoint.getSignature().toShortString().replace("(..)", "()");
        log.info("{} context:{} [method:{}, args:{}]", loggingType, context, methodName, args);
    }

    // Pointcut che rappresenta i metodi degli adapters per messaging
    @Pointcut("execution(* esperimenti.templateservice.msgAdapters.*.*(..))")
    public void msgCalls() {}

    // Eseguito prima dell'esecuzione del metodo
    @Before("msgCalls()")
    public void logBeforeExecuteMethod(JoinPoint joinPoint) {
        logWithType(joinPoint, LoggingType.REQUEST);
    }

    // Eseguito quando il metodo è terminato (con successo)
    @AfterReturning(value = "msgCalls()")
    public void logSuccessMethod(JoinPoint joinPoint) {
        logWithType(joinPoint,LoggingType.SUCCESS);
    }

    // Eseguito se è stata sollevata un'eccezione
    @AfterThrowing("msgCalls()")
    public void logErrorApplication(JoinPoint joinPoint) {
        logWithType(joinPoint,LoggingType.FAIL);
    }
}
