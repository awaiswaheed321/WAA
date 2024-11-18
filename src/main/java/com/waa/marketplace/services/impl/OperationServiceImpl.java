package com.waa.marketplace.services.impl;

import com.waa.marketplace.entites.Operation;
import com.waa.marketplace.repositories.OperationRepository;
import com.waa.marketplace.services.OperationService;
import com.waa.marketplace.utils.SecurityUtils;
import org.aspectj.lang.JoinPoint;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class OperationServiceImpl implements OperationService {

    private final OperationRepository operationLoggerRepository;

    @Autowired
    public OperationServiceImpl(OperationRepository operationLoggerRepository) {
        this.operationLoggerRepository = operationLoggerRepository;
    }

    @Override
    public void log(JoinPoint joinPoint) {
        Operation operation = new Operation();
        operation.setPrinciple(SecurityUtils.getPrinciple());
        operation.setOperation(joinPoint.getSignature().getDeclaringTypeName() + "." + joinPoint.getSignature().getName());
        operationLoggerRepository.save(operation);
    }
}
