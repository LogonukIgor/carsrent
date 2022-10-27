package by.logonuk.aop;

import org.apache.log4j.Logger;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.AfterReturning;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.stereotype.Component;

@Component
@Aspect
public class LicenceAspect {

    private static final Logger log = Logger.getLogger(LicenceAspect.class);

    private static final String METHOD_FINISHED = "Method %s finished";

    @Pointcut("execution(public * by.logonuk.controller.DrivingLicenceController.createLicence(..))")
    public void aroundCreateLicencePointcut() {
    }

    @Pointcut("execution(public * by.logonuk.controller.DrivingLicenceController.updateLicence(..))")
    public void aroundUpdateLicencePointcut() {
    }

    @Pointcut("execution(public * by.logonuk.controller.DrivingLicenceController.softLicenceDelete(..))")
    public void aroundDeleteLicencePointcut() {
    }

    @AfterReturning(pointcut = "aroundCreateLicencePointcut()")
    public void logAroundCreateMethod(JoinPoint joinPoint) {
        log.info(String.format(METHOD_FINISHED, joinPoint.getSignature().getName()));
    }

    @AfterReturning(pointcut = "aroundUpdateLicencePointcut()")
    public void logAroundUpdateMethod(JoinPoint joinPoint) {
        log.info(String.format(METHOD_FINISHED, joinPoint.getSignature().getName()));
    }

    @AfterReturning(pointcut = "aroundDeleteLicencePointcut()")
    public void logAroundMethods(JoinPoint joinPoint) {
        log.info(String.format(METHOD_FINISHED, joinPoint.getSignature().getName()));
    }
}
