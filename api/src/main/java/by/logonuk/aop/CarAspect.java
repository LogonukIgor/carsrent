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

    @Pointcut("execution(public * by.logonuk.controller.CarController.createCar(..))")
    public void afterCreateCarPointcut() {
    }

    @Pointcut("execution(public * by.logonuk.controller.CarController.updateCar(..))")
    public void afterUpdateCarPointcut() {
    }

    @Pointcut("execution(public * by.logonuk.controller.CarController.softCarDelete(..))")
    public void afterDeleteCarPointcut() {
    }

    @AfterReturning(pointcut = "afterCreateCarPointcut()")
    public void logAfterCreateMethod(JoinPoint joinPoint) {
        log.info(String.format(METHOD_FINISHED, joinPoint.getSignature().getName()));
    }

    @AfterReturning(pointcut = "afterUpdateCarPointcut()")
    public void logAfterUpdateMethod(JoinPoint joinPoint) {
        log.info(String.format(METHOD_FINISHED, joinPoint.getSignature().getName()));
    }

    @AfterReturning(pointcut = "afterDeleteCarPointcut()")
    public void logAfterMethods(JoinPoint joinPoint) {
        log.info(String.format(METHOD_FINISHED, joinPoint.getSignature().getName()));
    }
}
