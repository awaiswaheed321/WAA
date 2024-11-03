package edu.miu.labs.aspects;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import java.util.Arrays;

@Aspect
@Component
public class ExecutionTimeAspect {
    private static final Logger LOGGER = LoggerFactory.getLogger(ExecutionTimeAspect.class);

    @Pointcut("@annotation(edu.miu.labs.aspects.annotations.ExecutionTime)")
    public void executionTimePointCut() {
    }

    @Around("executionTimePointCut()")
    public Object logExecutionTime(ProceedingJoinPoint joinPoint) throws Throwable {
        long startTime = System.nanoTime();
        Object result;
        try {
            result = joinPoint.proceed();
        } catch (Throwable throwable) {
            LOGGER.error("Exception in method: {} with cause = {}", joinPoint.getSignature(), throwable.getCause() != null ? throwable.getCause() : "NULL");
            throw throwable;
        }
        long endTime = System.nanoTime();
        long durationInMillis = java.time.Duration.ofNanos(endTime - startTime).toMillis();
        String methodName = joinPoint.getSignature().getDeclaringTypeName() + "." + joinPoint.getSignature().getName();
        Object[] methodArgs = joinPoint.getArgs();
        LOGGER.info("Execution of {} with args = {} took {} ms", methodName, Arrays.toString(methodArgs), durationInMillis);
        return result;
    }

}
