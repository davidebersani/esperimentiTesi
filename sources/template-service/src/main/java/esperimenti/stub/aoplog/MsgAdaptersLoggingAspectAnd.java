package esperimenti.stub.aoplog;

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
    @Pointcut("execution(* esperimenti.templateservice.msgAdapters.*.*(..))")
    public void getPointcut() {

    }
}
