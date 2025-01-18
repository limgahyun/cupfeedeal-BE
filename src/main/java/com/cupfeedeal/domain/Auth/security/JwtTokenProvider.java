package com.cupfeedeal.domain.Auth.security;

import com.cupfeedeal.domain.Auth.dto.responseDto.JwtTokenResponseDto;
import io.jsonwebtoken.*;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import jakarta.annotation.PostConstruct;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.jetbrains.annotations.NotNull;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Component;
import org.springframework.security.core.authority.SimpleGrantedAuthority;

import javax.swing.*;
import java.security.Key;
import java.util.Arrays;
import java.util.Base64;
import java.util.Collection;
import java.util.Date;
import java.util.stream.Collectors;

import static javax.crypto.Cipher.SECRET_KEY;

@Slf4j
@Component
public class JwtTokenProvider {

//    @Value("${JWT_SECRET_KEY")
//    private String secretKey;
    byte[] keyBytes = Keys.secretKeyFor(SignatureAlgorithm.HS256).getEncoded();
    String secretKey = Base64.getEncoder().encodeToString(keyBytes);

    private final UserDetailsService userDetailsService;

    public JwtTokenProvider(UserDetailsService userDetailsService) {
        this.userDetailsService = userDetailsService;
    }

    //토큰 생성
    public String createToken(String username, Integer subscription_count) {
        Date now = new Date();

        return Jwts.builder()
                .setSubject(username)
                .claim("subscription_count", subscription_count)
                .setIssuedAt(now) // 토큰 발행 시간 정보
                .setExpiration(new Date(now.getTime() + (30 * 60 * 1000L))) // 토큰 유효시각 설정 (30분)
                .signWith(SignatureAlgorithm.HS256, secretKey)
                .compact();
    }

    public String regenerateToken(String token, String newUsername) {
        Claims claims = Jwts.parser()
                .setSigningKey(secretKey)
                .parseClaimsJws(token)
                .getBody();

        claims.setSubject(newUsername);

        return Jwts.builder()
                .setClaims(claims) // 수정된 클레임 사용
                .setIssuedAt(new Date()) // 발행 시간 갱신
                .setExpiration(claims.getExpiration()) // 기존 만료 시간 유지
                .signWith(SignatureAlgorithm.HS256, secretKey)
                .compact();
    }

    // 인증 정보 조회
    public Authentication getAuthentication(String token) {
        String username = this.getUsername(token);

        if (username == null || username.isBlank()) {
            System.out.println("토큰에서 추출한 이름이 유효하지 않습니다: " + token);
            throw new IllegalArgumentException("유효하지 않은 token.");
        }

        System.out.println("토큰에서 추출한 이름: " + username);

        UserDetails userDetails = userDetailsService.loadUserByUsername(username);
        return new UsernamePasswordAuthenticationToken(userDetails, "", userDetails.getAuthorities());
    }

    // 토큰에서 회원 정보 추출
    public String getUsername(String token) {
        Claims claims = Jwts.parser()
                .setSigningKey(secretKey)
                .parseClaimsJws(token)
                .getBody();
        return claims.getSubject();
    }

    // 토큰 유효성, 만료일자 확인
    public boolean validateToken(String token) {
        try {
            Jwts.parser().setSigningKey(secretKey).parseClaimsJws(token);
            return true;
        } catch (Exception e) {
            return false;
        }
    }

    // Request의 Header에서 token 값 가져오기
    public String resolveToken(HttpServletRequest request) {
        return request.getHeader("X-AUTH-TOKEN");
    }
}