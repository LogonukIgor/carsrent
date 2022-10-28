package by.logonuk.aop;

import org.apache.log4j.Logger;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.AfterReturning;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.stereotype.Component;

@Component
@Aspect
public class CarAspect {

    private static final Logger log = Logger.getLogger(CarAspect.class);

    private static final String METHOD_FINISHED = "Method %s finished";

    @Pointcut("execution(public * by.logonuk.controller.CarController.*(..))")
    public void afterCarPointcut() {
    }

    @AfterReturning(pointcut = "afterCarPointcut()")
    public void logAfterCarsMethod(JoinPoint joinPoint) {
        log.info(String.format(METHOD_FINISHED, joinPoint.getSignature().getName()));
    }
}
