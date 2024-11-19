package com.waa.marketplace.services.impl;

import com.waa.marketplace.entites.ExceptionEntity;
import com.waa.marketplace.repositories.ExceptionEntityRepository;
import com.waa.marketplace.services.ExceptionsEntityService;
import com.waa.marketplace.utils.SecurityUtils;
import org.aspectj.lang.JoinPoint;
import org.springframework.stereotype.Service;

@Service
public class ExceptionEntityServiceImpl implements ExceptionsEntityService {
    private final ExceptionEntityRepository exceptionEntityRepository;

    public ExceptionEntityServiceImpl(ExceptionEntityRepository exceptionEntityRepository) {
        this.exceptionEntityRepository = exceptionEntityRepository;
    }

    @Override
    public void log(JoinPoint joinPoint, Exception exception) {
        ExceptionEntity exceptionEntity = new ExceptionEntity();
        exceptionEntity.setOperation(joinPoint.getSignature().getDeclaringTypeName() + "." + joinPoint.getSignature().getName());
        exceptionEntity.setExceptionType(exception.getClass().getName());
        exceptionEntity.setPrinciple(SecurityUtils.getUsername());
        String exceptionMessage = exception.getMessage();
        if (exceptionMessage != null && exceptionMessage.length() > 1000) {
            exceptionMessage = exceptionMessage.substring(0, 1000);
        }
        exceptionEntity.setMessage(exceptionMessage);
        exceptionEntityRepository.save(exceptionEntity);
    }
}
