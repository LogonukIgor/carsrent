package by.logonuk.aop;

import org.apache.log4j.Logger;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.AfterReturning;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.stereotype.Component;

@Component
@Aspect
public class ExceptionAspect {

    private static final Logger log = Logger.getLogger(ExceptionAspect.class);

    private static final String EXCEPTION = "Exception %s";

    @Pointcut("execution(public * by.logonuk.exceptionhandle.DefaultExceptionHandler*(..))")
    public void afterExceptionPointcut() {
    }

    @AfterReturning(pointcut = "afterExceptionPointcut()")
    public void logAfterExceptionMethod(JoinPoint joinPoint) {
        log.warn(String.format(EXCEPTION, joinPoint.getSignature().getName()));
    }
}
