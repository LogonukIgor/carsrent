package by.logonuk.aop;

import org.apache.log4j.Logger;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.stereotype.Component;

@Component
@Aspect
public class UserAspect {

    private static final Logger log = Logger.getLogger(UserAspect.class);

    private static final String METHOD_FINISHED = "Method %s finished";
    private static final String METHOD_START = "Method %s start";

    @Pointcut("execution(public * by.logonuk.controller.UserController.*(..))")
    public void afterUserPointcut() {
    }

    @Around("afterUserPointcut()")
    public Object logAroundMethods(ProceedingJoinPoint joinPoint) throws Throwable {
        log.info(String.format(METHOD_START, joinPoint.getSignature().getName()));
        Object proceed = joinPoint.proceed();
        log.info(String.format(METHOD_FINISHED, joinPoint.getSignature().getName()));
        return proceed;
    }
}
