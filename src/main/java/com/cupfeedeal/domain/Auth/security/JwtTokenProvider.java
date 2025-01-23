package com.cupfeedeal.domain.Auth.security;

import com.cupfeedeal.domain.User.entity.User;
import com.cupfeedeal.domain.User.repository.UserRepository;
import io.jsonwebtoken.*;
import io.jsonwebtoken.security.Keys;
import jakarta.servlet.http.HttpServletRequest;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;

import java.util.Base64;
import java.util.Date;

@Slf4j
@Component
public class JwtTokenProvider {

//    @Value("${JWT_SECRET_KEY")
//    private String secretKey;
    byte[] keyBytes = Keys.secretKeyFor(SignatureAlgorithm.HS256).getEncoded();
    String secretKey = Base64.getEncoder().encodeToString(keyBytes);

    private final UserDetailsService userDetailsService;
    private final UserRepository userRepository;

    public JwtTokenProvider(UserDetailsService userDetailsService, UserRepository userRepository) {
        this.userDetailsService = userDetailsService;
        this.userRepository = userRepository;
    }

    //토큰 생성
    public String createToken(Long userId) {
        Date now = new Date();

        return Jwts.builder()
                .setSubject(String.valueOf(userId))
                .setIssuedAt(now) // 토큰 발행 시간 정보
                .setExpiration(new Date(now.getTime() + (1000L * 60 * 60 * 5))) // 토큰 유효시각 설정 (5시간)
                .signWith(SignatureAlgorithm.HS256, secretKey)
                .compact();
    }

    // 인증 정보 조회
    public Authentication getAuthentication(String token) {
        Long userId = Long.valueOf(this.getUserId(token));

        System.out.println("토큰에서 추출한 userId: " + userId);

        User user = userRepository.findById(userId)
                .orElseThrow(() -> new UsernameNotFoundException("해당 ID의 사용자를 찾을 수 없습니다: " + userId));
        String username = user.getUsername();

        UserDetails userDetails = userDetailsService.loadUserByUsername(username);
        return new UsernamePasswordAuthenticationToken(userDetails, "", userDetails.getAuthorities());
    }

    // 토큰에서 회원 정보 추출
    public String getUserId(String token) {
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