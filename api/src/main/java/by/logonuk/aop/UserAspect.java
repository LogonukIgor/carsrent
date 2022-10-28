package by.logonuk.aop;

import org.apache.log4j.Logger;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.AfterReturning;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.stereotype.Component;

@Component
@Aspect
public class UserAspect {

    private static final Logger log = Logger.getLogger(UserAspect.class);

    private static final String METHOD_FINISHED = "Method %s finished";

    @Pointcut("execution(public * by.logonuk.controller.UserController.createUser(..))")
    public void afterCreateUserPointcut() {
    }

    @Pointcut("execution(public * by.logonuk.controller.UserController.updateUser(..))")
    public void afterUpdateUserPointcut() {
    }

    @Pointcut("execution(public * by.logonuk.controller.UserController.softUserDelete(..))")
    public void afterDeleteUserPointcut() {
    }

    @AfterReturning(pointcut = "afterCreateUserPointcut()")
    public void logAfterCreateMethod(JoinPoint joinPoint) {
        log.info(String.format(METHOD_FINISHED, joinPoint.getSignature().getName()));
    }

    @AfterReturning(pointcut = "afterUpdateUserPointcut()")
    public void logAfterUpdateMethod(JoinPoint joinPoint) {
        log.info(String.format(METHOD_FINISHED, joinPoint.getSignature().getName()));
    }

    @AfterReturning(pointcut = "afterDeleteUserPointcut()")
    public void logAfterMethods(JoinPoint joinPoint) {
        log.info(String.format(METHOD_FINISHED, joinPoint.getSignature().getName()));
    }
}
