package edu.miu.labs.aspects;

import edu.miu.labs.service.OperationService;
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

    @Pointcut("execution( * edu.miu.labs.controllers.*.*(..))")
    public void controllerAnyMethodPointcut() {
    }

    @Before("controllerAnyMethodPointcut()")
    public void logAfter(JoinPoint joinPoint) {
        this.operationLoggerService.log(joinPoint);
    }
}
