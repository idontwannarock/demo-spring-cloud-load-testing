package com.example.demoloadtestingapp.security;

public class SecurityConstants {

    public static final String LOGIN_URI = "/login";

    // Signing key for HS512 algorithm
    // You can use the page http://www.allkeysgenerator.com/ to generate all kinds of keys
    public static final String SECRET = "B?E(H+MbQeThWmYq3t6w9z$C&F)J@NcRfUjXn2r5u7x!A%D*G-KaPdSgVkYp3s6v";

    // JWT token defaults
    public static final String HEADER = "CustomAuthorization"; // for better showing the result, should be Authorization
    public static final String PREFIX = "Bearer ";
    public static final int EXPIRATION_TIME = 24 * 60 * 60; // in seconds
    public static final String ROLES = "rol";
}