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
    public void aroundCreateCarPointcut() {
    }

    @Pointcut("execution(public * by.logonuk.controller.CarController.updateCar(..))")
    public void aroundUpdateCarPointcut() {
    }

    @Pointcut("execution(public * by.logonuk.controller.CarController.softCarDelete(..))")
    public void aroundDeleteCarPointcut() {
    }

    @AfterReturning(pointcut = "aroundCreateCarPointcut()")
    public void logAroundCreateMethod(JoinPoint joinPoint) {
        log.info(String.format(METHOD_FINISHED, joinPoint.getSignature().getName()));
    }

    @AfterReturning(pointcut = "aroundUpdateCarPointcut()")
    public void logAroundUpdateMethod(JoinPoint joinPoint) {
        log.info(String.format(METHOD_FINISHED, joinPoint.getSignature().getName()));
    }

    @AfterReturning(pointcut = "aroundDeleteCarPointcut()")
    public void logAroundMethods(JoinPoint joinPoint) {
        log.info(String.format(METHOD_FINISHED, joinPoint.getSignature().getName()));
    }
}
