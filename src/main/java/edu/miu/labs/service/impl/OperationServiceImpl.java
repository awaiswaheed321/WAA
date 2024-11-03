package edu.miu.labs.service.impl;

import edu.miu.labs.entities.Operation;
import edu.miu.labs.repositories.OperationRepository;
import edu.miu.labs.service.OperationService;
import edu.miu.labs.utils.SecurityUtils;
import org.aspectj.lang.JoinPoint;
import org.springframework.stereotype.Service;

@Service
public class OperationServiceImpl implements OperationService {

    private final OperationRepository operationLoggerRepository;

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
