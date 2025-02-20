package com.cupfeedeal.domain.Auth.security;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.ServletRequest;
import jakarta.servlet.ServletResponse;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.util.StringUtils;
import org.springframework.web.filter.GenericFilterBean;

import java.io.IOException;

@Slf4j
@RequiredArgsConstructor
public class JwtAuthenticationFilter extends GenericFilterBean {
    private final JwtTokenProvider jwtTokenProvider;

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {

        // 1. Request Header에서 JWT 토큰 추출
        String token = resolveToken((HttpServletRequest) servletRequest);

        if (token != null && jwtTokenProvider.validateToken(token)) {
            Authentication authentication = jwtTokenProvider.getAuthentication(token);
            SecurityContextHolder.getContext().setAuthentication(authentication);
            log.info("User authenticated: {}", authentication.getName());
            log.info("Authorities: {}", authentication.getAuthorities());
        } else {
            log.warn("Invalid or missing JWT token.");
        }

        filterChain.doFilter(servletRequest, servletResponse);
    }

    // Request Header에서 토큰 정보 추출
    private String resolveToken(HttpServletRequest request) {
        String bearerToken = request.getHeader("Authorization");
        if (bearerToken == null) {
            log.warn("Authorization header is completely missing in the request.");
        } else if (!bearerToken.startsWith("Bearer ")) {
            log.warn("Authorization header is malformed. Value: {}", bearerToken);
        } else {
            log.info("Authorization header found and valid.");
        }
        return StringUtils.hasText(bearerToken) && bearerToken.startsWith("Bearer ")
                ? bearerToken.substring(7)
                : null;
    }

}