package edu.miu.labs.utils;

public final class Constants {
    private Constants() {
    }

    public static final long ACCESS_TOKEN_EXPIRATION = 15 * 60 * 1000; // 15 minutes
    public static final long REFRESH_TOKEN_EXPIRATION = 7 * 24 * 60 * 60 * 1000; // 7 days
    public static final String JWT_SECRET = "f0943ff2-0e9d-4fef-b830-537bde464e5d";
}
