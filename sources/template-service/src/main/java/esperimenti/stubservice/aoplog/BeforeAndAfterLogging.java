package esperimenti.stubservice.aoplog;

import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.*;

import java.util.Arrays;

@Slf4j
@Aspect
public abstract class BeforeAndAfterLogging {

    private String context;

    public BeforeAndAfterLogging() {
        context = getContext();
    }

    /**
     * Il metodo deve ritornare il contesto che deve essere stampato nel log.
     * @return Contesto del logging
     */
    abstract public String getContext();

    /**
     * Metodo vuoto che definisce il pointcut, DEVE ESSERE ANNOTATO con @Pointcut(<pointcut>) per definire il pointcut.
     * @return Pointcut
     */
    abstract public void getPointcut();


    private void logWithType(JoinPoint joinPoint, LoggingType loggingType) {
        final String args = Arrays.toString(joinPoint.getArgs());
        final String methodName = joinPoint.getSignature().toShortString().replace("(..)", "()");
        log.info("{} context:{} [method:{}, args:{}]", loggingType, context, methodName, args);
    }

    private void logFailure(JoinPoint joinPoint, LoggingType loggingType, Exception e) {
        final String args = Arrays.toString(joinPoint.getArgs());
        final String methodName = joinPoint.getSignature().toShortString().replace("(..)", "()");
        log.error("{} context:{} [method:{}, args:{}, expception:{}, message:{}]", loggingType, context , methodName, args, e.getClass().getName(), e.getMessage());
    }

    // Eseguito prima dell'esecuzione del metodo
    @Before("getPointcut()")
    public void logBeforeExecuteMethod(JoinPoint joinPoint) {
        logWithType(joinPoint, LoggingType.REQUEST);
    }

    // Eseguito quando il metodo è terminato (con successo)
    @AfterReturning(value = "getPointcut()")
    public void logSuccessMethod(JoinPoint joinPoint) {
        logWithType(joinPoint,LoggingType.SUCCESS);
    }

    // Eseguito se è stata sollevata un'eccezione
    @AfterThrowing(value="getPointcut()", throwing = "e")
    public void logErrorApplication(JoinPoint joinPoint, Exception e) {
        logFailure(joinPoint,LoggingType.FAIL, e);
    }
}
