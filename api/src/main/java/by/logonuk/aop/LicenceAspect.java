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
    public void afterCreateLicencePointcut() {
    }

    @Pointcut("execution(public * by.logonuk.controller.DrivingLicenceController.updateLicence(..))")
    public void afterUpdateLicencePointcut() {
    }

    @Pointcut("execution(public * by.logonuk.controller.DrivingLicenceController.softLicenceDelete(..))")
    public void afterDeleteLicencePointcut() {
    }

    @AfterReturning(pointcut = "afterCreateLicencePointcut()")
    public void logAfterCreateMethod(JoinPoint joinPoint) {
        log.info(String.format(METHOD_FINISHED, joinPoint.getSignature().getName()));
    }

    @AfterReturning(pointcut = "afterUpdateLicencePointcut()")
    public void logAfterUpdateMethod(JoinPoint joinPoint) {
        log.info(String.format(METHOD_FINISHED, joinPoint.getSignature().getName()));
    }

    @AfterReturning(pointcut = "afterDeleteLicencePointcut()")
    public void logAfterMethods(JoinPoint joinPoint) {
        log.info(String.format(METHOD_FINISHED, joinPoint.getSignature().getName()));
    }
}
