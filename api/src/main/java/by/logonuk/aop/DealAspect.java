package by.logonuk.aop;

import org.apache.log4j.Logger;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.AfterReturning;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.stereotype.Component;

@Component
@Aspect
public class DealAspect {

    private static final Logger log = Logger.getLogger(DealAspect.class);

    private static final String METHOD_FINISHED = "Method %s finished";

    @Pointcut("execution(public * by.logonuk.controller.DealController.createDeal(..))")
    public void afterCreateDealPointcut() {
    }

    @Pointcut("execution(public * by.logonuk.controller.DealController.updateDeal(..))")
    public void afterUpdateDealPointcut() {
    }

    @Pointcut("execution(public * by.logonuk.controller.DealController.deleteDeal(..))")
    public void afterDeleteDealPointcut() {
    }

    @AfterReturning(pointcut = "afterCreateDealPointcut()")
    public void logAfterCreateMethod(JoinPoint joinPoint) {
        log.info(String.format(METHOD_FINISHED, joinPoint.getSignature().getName()));
    }

    @AfterReturning(pointcut = "afterUpdateDealPointcut()")
    public void logAfterUpdateMethod(JoinPoint joinPoint) {
        log.info(String.format(METHOD_FINISHED, joinPoint.getSignature().getName()));
    }

    @AfterReturning(pointcut = "afterDeleteDealPointcut()")
    public void logAfterMethods(JoinPoint joinPoint) {
        log.info(String.format(METHOD_FINISHED, joinPoint.getSignature().getName()));
    }
}
