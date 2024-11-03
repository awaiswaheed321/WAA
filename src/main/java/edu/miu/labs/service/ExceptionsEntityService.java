package edu.miu.labs.service;

import org.aspectj.lang.JoinPoint;

public interface ExceptionsEntityService {
    void log(JoinPoint joinPoint, Exception exception);
}
