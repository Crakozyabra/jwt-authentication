package com.example.jwt.filter;

import com.example.jwt.util.JwtTokenUtil;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.SignatureException;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.util.stream.Collectors;

@Component
@RequiredArgsConstructor
@Slf4j
public class JwtRequestFilter extends OncePerRequestFilter {

    private static final String AUTHORIZATION_HEADER_VALUE_PREFIX = "Bearer ";

    private static final int AUTHORIZATION_HEADER_VALUE_PREFIX_LENGTH = AUTHORIZATION_HEADER_VALUE_PREFIX.length();

    private final JwtTokenUtil jwtTokenUtil;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
            throws ServletException, IOException {
        String authHeader = request.getHeader("Authorization");
        String username = null;
        String jwt = null;
        if (authHeader != null && authHeader.startsWith(AUTHORIZATION_HEADER_VALUE_PREFIX)) {
            jwt = authHeader.substring(AUTHORIZATION_HEADER_VALUE_PREFIX_LENGTH);
            try {
                username = jwtTokenUtil.getUsername(jwt);
            } catch (ExpiredJwtException e) {
                log.info("ExpiredJwtException");
            } catch (SignatureException e) {
                log.info("SignatureException");
            }
        }

        if (username != null && SecurityContextHolder.getContext().getAuthentication() == null) {
            SecurityContextHolder
                    .getContext()
                    .setAuthentication(
                            new UsernamePasswordAuthenticationToken(username,
                                    null,
                                    jwtTokenUtil.getAuthorities(jwt).stream()
                                            .map(SimpleGrantedAuthority::new)
                                            .collect(Collectors.toList()))
                    );
        }
        filterChain.doFilter(request, response);
    }
}