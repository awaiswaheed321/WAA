package edu.miu.labs.utils;

public final class SecurityUtils {
    private SecurityUtils() {
    }

    //    Will be implemented in future after spring security
    public static String getPrinciple() {
        return "Anonymous";
    }
}