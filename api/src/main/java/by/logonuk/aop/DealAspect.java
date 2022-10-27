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
    public void aroundCreateDealPointcut() {
    }

    @Pointcut("execution(public * by.logonuk.controller.DealController.updateDeal(..))")
    public void aroundUpdateDealPointcut() {
    }

    @Pointcut("execution(public * by.logonuk.controller.DealController.deleteDeal(..))")
    public void aroundDeleteDealPointcut() {
    }

    @AfterReturning(pointcut = "aroundCreateDealPointcut()")
    public void logAroundCreateMethod(JoinPoint joinPoint) {
        log.info(String.format(METHOD_FINISHED, joinPoint.getSignature().getName()));
    }

    @AfterReturning(pointcut = "aroundUpdateDealPointcut()")
    public void logAroundUpdateMethod(JoinPoint joinPoint) {
        log.info(String.format(METHOD_FINISHED, joinPoint.getSignature().getName()));
    }

    @AfterReturning(pointcut = "aroundDeleteDealPointcut()")
    public void logAroundMethods(JoinPoint joinPoint) {
        log.info(String.format(METHOD_FINISHED, joinPoint.getSignature().getName()));
    }
}
