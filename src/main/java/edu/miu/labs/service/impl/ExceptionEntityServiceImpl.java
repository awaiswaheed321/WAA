package edu.miu.labs.service.impl;

import edu.miu.labs.entities.ExceptionEntity;
import edu.miu.labs.repositories.ExceptionEntityRepository;
import edu.miu.labs.service.ExceptionsEntityService;
import edu.miu.labs.utils.SecurityUtils;
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
        exceptionEntity.setPrinciple(SecurityUtils.getPrinciple());
        exceptionEntity.setMessage(exception.getMessage());
        exceptionEntityRepository.save(exceptionEntity);
    }
}
