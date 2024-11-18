package com.waa.marketplace.services;

import org.aspectj.lang.JoinPoint;

public interface ExceptionsEntityService {
    void log(JoinPoint joinPoint, Exception exception);
}
