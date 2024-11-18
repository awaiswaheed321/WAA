package com.waa.marketplace.utils;

public final class Constants {
    private Constants() {
    }

    public static final long ACCESS_TOKEN_EXPIRATION = 15 * 60 * 1000; // 15 minutes
    public static final long REFRESH_TOKEN_EXPIRATION = 7 * 24 * 60 * 60 * 1000; // 7 days
    public static final String JWT_SECRET = "b6c79464-e139-4991-8fd0-cbc0c6a199da";
}
