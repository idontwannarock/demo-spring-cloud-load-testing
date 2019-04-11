package com.example.demoloadtestingapp.security;

import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.springframework.security.core.GrantedAuthority;

import java.util.Collection;
import java.util.Date;

public class JwtUtil {

    public static String createToken(String accountId, Collection<? extends GrantedAuthority> authorities) {
        return Jwts.builder()
                .setSubject(accountId)
                .claim(SecurityConstants.ROLES, authorities)
                .setIssuedAt(new Date())
                .setExpiration(new Date(System.currentTimeMillis() + SecurityConstants.EXPIRATION_TIME * 1000))
                .signWith(SignatureAlgorithm.HS512, SecurityConstants.SECRET.getBytes())
                .compact();
    }
}
