package com.example.demoloadtestingapp.security;

import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Collections;

public class LoginAuthenticationFilter extends UsernamePasswordAuthenticationFilter {

    private AuthenticationManager authManager;

    LoginAuthenticationFilter(AuthenticationManager authenticationManager) {
        this.authManager = authenticationManager;
        // By default, UsernamePasswordAuthenticationFilter listens to "/login" path.
        // So if you need to customize the login uri, you need to override the defaults.
        this.setRequiresAuthenticationRequestMatcher(new AntPathRequestMatcher(SecurityConstants.LOGIN_URI, HttpMethod.POST.name()));
    }

    @Override
    public Authentication attemptAuthentication(HttpServletRequest request, HttpServletResponse response) throws AuthenticationException {
        System.out.println("Starts to attemp authentication for user " + request.getParameter("username") + ".");
        // Authentication manager authenticate the user, and use in-memory authentication to load the user.
        return authManager.authenticate(new UsernamePasswordAuthenticationToken(
                request.getParameter("username"), request.getParameter("password"), Collections.emptyList()));
    }

    // Upon successful authentication, generate a token.
    // The 'auth' passed to successfulAuthentication() is the current authenticated user.
    @Override
    protected void successfulAuthentication(HttpServletRequest request, HttpServletResponse response, FilterChain chain, Authentication auth) throws IOException, ServletException {
        System.out.println("User " + request.getParameter("username") + " is successfully authenticated.");
        // Add token to header
        response.addHeader(SecurityConstants.HEADER, SecurityConstants.PREFIX + JwtUtil.createToken(auth.getName(), auth.getAuthorities()));
    }
}