package com.waa.marketplace.services;

import org.aspectj.lang.JoinPoint;

public interface OperationService {
    void log(JoinPoint joinPoint);
}
