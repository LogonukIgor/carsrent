package by.logonuk.aop;

import org.apache.log4j.Logger;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.stereotype.Component;

@Component
@Aspect
public class LicenceAspect {

    private static final Logger log = Logger.getLogger(LicenceAspect.class);

    private static final String METHOD_FINISHED = "Method %s finished";

    private static final String METHOD_START = "Method %s start";

    @Pointcut("execution(public * by.logonuk.controller.DrivingLicenceController.createLicence(..))")
    public void afterLicenceCreatePointcut() {
    }

    @Pointcut("execution(public * by.logonuk.controller.DrivingLicenceController.updateLicence(..))")
    public void afterLicenceUpdatePointcut() {
    }

    @Pointcut("execution(public * by.logonuk.controller.DrivingLicenceController.deleteLicence(..))")
    public void afterLicenceDeletePointcut() {
    }

    @Around("afterLicenceCreatePointcut()")
    public Object logAroundCreateMethod(ProceedingJoinPoint joinPoint) throws Throwable {
        log.info(String.format(METHOD_START, joinPoint.getSignature().getName()));
        Object proceed = joinPoint.proceed();
        log.info(String.format(METHOD_FINISHED, joinPoint.getSignature().getName()));
        return proceed;
    }

    @Around("afterLicenceUpdatePointcut()")
    public Object logAroundUpdateMethod(ProceedingJoinPoint joinPoint) throws Throwable {
        log.info(String.format(METHOD_START, joinPoint.getSignature().getName()));
        Object proceed = joinPoint.proceed();
        log.info(String.format(METHOD_FINISHED, joinPoint.getSignature().getName()));
        return proceed;
    }

    @Around("afterLicenceDeletePointcut()")
    public Object logAroundDeleteMethod(ProceedingJoinPoint joinPoint) throws Throwable {
        log.info(String.format(METHOD_START, joinPoint.getSignature().getName()));
        Object proceed = joinPoint.proceed();
        log.info(String.format(METHOD_FINISHED, joinPoint.getSignature().getName()));
        return proceed;
    }
}
