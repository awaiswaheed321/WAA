package com.waa.marketplace.aspects;

import com.waa.marketplace.services.OperationService;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.stereotype.Component;

@Aspect
@Component
public class OperationAspect {
    private final OperationService operationLoggerService;

    public OperationAspect(OperationService operationLoggerService) {
        this.operationLoggerService = operationLoggerService;
    }

    @Pointcut("execution( * com.waa.marketplace.controllers.*.*(..))")
    public void controllerAnyMethodPointcut() {
    }

    @Before("controllerAnyMethodPointcut()")
    public void logAfter(JoinPoint joinPoint) {
        this.operationLoggerService.log(joinPoint);
    }
}
