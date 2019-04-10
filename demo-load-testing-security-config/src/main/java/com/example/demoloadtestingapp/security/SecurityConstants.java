package com.example.demoloadtestingapp.security;

class SecurityConstants {

    static final String LOGIN_URI = "/login";

    // Signing key for HS512 algorithm
    // You can use the page http://www.allkeysgenerator.com/ to generate all kinds of keys
    static final String SECRET = "B?E(H+MbQeThWmYq3t6w9z$C&F)J@NcRfUjXn2r5u7x!A%D*G-KaPdSgVkYp3s6v";

    // JWT token defaults
    static final String HEADER = "CustomAuthorization"; // for better showing the result, should be Authorization
    static final String PREFIX = "Bearer ";
    static final int EXPIRATION_TIME = 24 * 60 * 60; // in seconds
    static final String ROLES = "rol";
}