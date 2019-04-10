package com.example.demoloadtestingapp.security;

import com.google.common.base.Strings;
import io.jsonwebtoken.*;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.www.BasicAuthenticationFilter;
import org.springframework.web.server.ResponseStatusException;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;
import java.util.stream.Collectors;

public class JwtAuthorizationFilter extends BasicAuthenticationFilter {

    JwtAuthorizationFilter(AuthenticationManager authenticationManager) {
        super(authenticationManager);
    }

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws IOException, ServletException {
        String header = request.getHeader(SecurityConstants.HEADER);
        if (!Strings.isNullOrEmpty(header) && header.startsWith(SecurityConstants.PREFIX)) {
            System.out.println("Starts to validate JWT token.");
            UsernamePasswordAuthenticationToken authentication = getAuthentication(request);
            SecurityContextHolder.getContext().setAuthentication(authentication);
        }
        filterChain.doFilter(request, response);
    }

    private UsernamePasswordAuthenticationToken getAuthentication(HttpServletRequest request) {
        String token = request.getHeader(SecurityConstants.HEADER);
        if (!Strings.isNullOrEmpty(token)) {
            try {
                Claims claims = Jwts.parser()
                        .setSigningKey(SecurityConstants.SECRET.getBytes())
                        .parseClaimsJws(token.replace(SecurityConstants.PREFIX, ""))
                        .getBody();

                String username = claims.getSubject();

                List<GrantedAuthority> authorities = ((List<?>) claims.get(SecurityConstants.ROLES))
                        .stream().map(authority -> new SimpleGrantedAuthority((String) authority)).collect(Collectors.toList());

                if (!Strings.isNullOrEmpty(username)) {
                    System.out.println("JWT token validated.");
                    return new UsernamePasswordAuthenticationToken(username, null, authorities);
                }
            } catch (ExpiredJwtException exception) {
                throw new ResponseStatusException(HttpStatus.UNAUTHORIZED, "Request to parse expired JWT : " + token + " failed : " + exception.getMessage());
            } catch (UnsupportedJwtException exception) {
                throw new ResponseStatusException(HttpStatus.UNAUTHORIZED, "Request to parse unsupported JWT : " + token + " failed : " + exception.getMessage());
            } catch (MalformedJwtException exception) {
                throw new ResponseStatusException(HttpStatus.UNAUTHORIZED, "Request to parse invalid JWT : " + token + " failed : " + exception.getMessage());
            } catch (SignatureException exception) {
                throw new ResponseStatusException(HttpStatus.UNAUTHORIZED, "Request to parse JWT with invalid signature : " + token + " failed : " + exception.getMessage());
            } catch (IllegalArgumentException exception) {
                throw new ResponseStatusException(HttpStatus.UNAUTHORIZED, "Request to parse empty or null JWT : " + token + " failed : " + exception.getMessage());
            }
        }
        return null;
    }
}
