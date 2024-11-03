package edu.miu.labs.service;

import org.aspectj.lang.JoinPoint;

public interface OperationService {
    void log(JoinPoint joinPoint);
}
