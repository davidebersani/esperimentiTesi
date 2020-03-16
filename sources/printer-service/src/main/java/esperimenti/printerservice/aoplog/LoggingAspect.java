package esperimenti.printerservice.aoplog;

import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.*;
import org.springframework.stereotype.Component;

import java.util.Arrays;


@Component
@Aspect
@Slf4j
public class LoggingAspect {


    private static void writeLog(JoinPoint joinPoint, LoggingType loggingType) {
        final String args = Arrays.toString(joinPoint.getArgs());
        final String methodName = joinPoint.getSignature().toShortString().replace("(..)", "()");
        log.info("{} [method:{}, args:{}]", loggingType, methodName, args);
    }

    // Pointcut che rappresenta i metodi dei controller Rest
    @Pointcut("execution(* esperimenti.printerservice.rest.*.*(..))")
    public void restCalls() {}

    // Eseguito prima dell'esecuzione del metodo
    @Before("restCalls()")
    public void logBeforeExecuteMethod(JoinPoint joinPoint) {
        writeLog(joinPoint, LoggingType.REQUEST);
    }

    // Eseguito quando il metodo è terminato (con successo)
    @AfterReturning(value = "restCalls()")
    public void logSuccessMethod(JoinPoint joinPoint) {
        writeLog(joinPoint,LoggingType.SUCCESS);
    }

    // Eseguito se è stata sollevata un'eccezione
    @AfterThrowing("restCalls()")
    public void logErrorApplication(JoinPoint joinPoint) {
        writeLog(joinPoint,LoggingType.FAIL);
    }

}
