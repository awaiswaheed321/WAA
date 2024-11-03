package edu.miu.labs.aspects;

import edu.miu.labs.service.ExceptionsEntityService;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.AfterThrowing;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.stereotype.Component;

@Aspect
@Component
public class ExceptionsAspect {
    private final ExceptionsEntityService exceptionsEntityService;

    public ExceptionsAspect(ExceptionsEntityService exceptionsEntityService) {
        this.exceptionsEntityService = exceptionsEntityService;
    }

    @Pointcut("execution( * edu.miu.labs.controllers.*.*(..))")
    public void controllerAnyMethodPointcut() {}

    @AfterThrowing(value = "controllerAnyMethodPointcut()", throwing = "exception")
    public void logException(JoinPoint joinPoint, Exception exception) {
        this.exceptionsEntityService.log(joinPoint, exception);
    }
}
