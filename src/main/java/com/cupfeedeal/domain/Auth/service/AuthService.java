package com.cupfeedeal.domain.Auth.service;

import com.cupfeedeal.domain.Auth.dto.KakaoDto;
import com.cupfeedeal.domain.Auth.util.JwtUtil;
import com.cupfeedeal.domain.Auth.util.SignInUtil;
import com.cupfeedeal.domain.User.entity.User;
import com.cupfeedeal.domain.User.repository.UserRepository;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AuthService {

    private final SignInUtil signInUtil;
    private final UserRepository userRepository;
//    private final JwtUtil jwtUtil;
    private final PasswordEncoder passwordEncoder;

    @Override
    public User OAuthSignIn(String accessCode, HttpServletResponse httpServletResponse){
        KakaoDto.OAuthToken oAuthToken = signInUtil.requestToken(accessCode);
        KakaoDto.KakaoProfile kakaoProfile = SignInUtil.requestProfile(oAuthToken);
    }

}
