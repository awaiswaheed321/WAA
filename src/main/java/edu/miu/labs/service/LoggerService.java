package edu.miu.labs.service;

public interface LoggerService {
    void info(String className, String message);

    void error(String className, String message, Throwable throwable);

    void warn(String className, String message);

    void debug(String className, String message);
}
