package edu.miu.labs.service.impl;

import edu.miu.labs.service.LoggerService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;

@Service
public class LoggerServiceImpl implements LoggerService {
    private final ConcurrentMap<String, Logger> loggers = new ConcurrentHashMap<>();

    private Logger getLogger(String className) {
        return loggers.computeIfAbsent(className, LoggerFactory::getLogger);
    }

    @Override
    public void info(String className, String message) {
        getLogger(className).info(message);
    }

    @Override
    public void error(String className, String message, Throwable throwable) {
        getLogger(className).error(message, throwable);
    }

    @Override
    public void warn(String className, String message) {
        getLogger(className).warn(message);
    }

    @Override
    public void debug(String className, String message) {
        getLogger(className).debug(message);
    }
}
