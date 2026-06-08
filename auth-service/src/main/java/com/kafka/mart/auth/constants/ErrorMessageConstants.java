package com.kafka.mart.auth.constants;

public final class ErrorMessageConstants {

    private ErrorMessageConstants() {
    }

    // User
    public static final String USER_NOT_FOUND =
            "User not found";

    public static final String EMAIL_ALREADY_EXISTS =
            "Email already exists";

    public static final String PHONE_ALREADY_EXISTS =
            "Phone already exists";

    // OTP
    public static final String OTP_NOT_FOUND =
            "OTP not found";

    public static final String INVALID_OTP =
            "Invalid OTP";

    public static final String OTP_EXPIRED =
            "OTP expired";

    // Token
    public static final String INVALID_TOKEN =
            "Invalid token";

    public static final String TOKEN_EXPIRED =
            "Token expired";

    public static final String INVALID_REFRESH_TOKEN =
            "Invalid refresh token";

    public static final String REFRESH_TOKEN_EXPIRED =
            "Refresh token expired";

    // Password
    public static final String INVALID_OLD_PASSWORD =
            "Old password is incorrect";

    // Auth
    public static final String ACCOUNT_LOCKED =
            "Account is locked";

    public static final String EMAIL_NOT_VERIFIED =
            "Please verify your email first";
}