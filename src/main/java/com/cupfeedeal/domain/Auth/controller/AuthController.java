package com.cupfeedeal.domain.Auth.controller;

import com.cupfeedeal.domain.Auth.dto.responseDto.KakaoUserInfoResponseDto;
import com.cupfeedeal.domain.Auth.security.JwtTokenProvider;
import com.cupfeedeal.domain.Auth.service.KakaoService;
import com.cupfeedeal.domain.User.entity.User;
import com.cupfeedeal.domain.User.repository.UserRepository;
import com.cupfeedeal.global.common.response.CommonResponse;
import io.jsonwebtoken.io.IOException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Optional;

@Slf4j
@RequiredArgsConstructor
@RestController
@RequestMapping("/api/v1/auth")
public class AuthController {

    private final KakaoService kakaoService;
    private final JwtTokenProvider jwtTokenProvider;
    private final UserRepository userRepository;

    @GetMapping("/callback")
    public CommonResponse<?> callback(@RequestParam("code") String code) throws IOException {
        String accessToken = kakaoService.getAccessTokenFromKakao(code);
        KakaoUserInfoResponseDto userInfo = kakaoService.getUserInfo(accessToken);
        String kakaoId = String.valueOf(userInfo.getId());
        String nickname = userInfo.getKakaoAccount().getProfile().getNickname();

        Optional<User> existingUser = userRepository.findByKakaoId(String.valueOf(userInfo.getId()));
        if (existingUser.isEmpty()) {
            User newUser = new User();
            newUser.setKakaoId(kakaoId);
            newUser.setUsername(nickname);
            newUser.setCreated_at(LocalDateTime.now());
            newUser.setUpdated_at(LocalDateTime.now());
            newUser.setUser_level(0);
            userRepository.save(newUser);
        }

        return new CommonResponse<>("카카오 로그인 코드가 성공적으로 수신되었습니다. ");
    }

    //카카오 로그인을 위해 회원가입 여부 확인, 이미 회원이면 JWT 토큰 발급
    @PostMapping("/signin")
    public CommonResponse<HashMap<String, Object>> authCheck(@RequestHeader String accessToken){
        KakaoUserInfoResponseDto userInfo = kakaoService.getUserInfo(accessToken);
        String kakaoId = String.valueOf(userInfo.getId());
        String nickname = userInfo.getKakaoAccount().getProfile().getNickname();

        Optional<User> optionalUser = userRepository.findByKakaoId(kakaoId);
        User user = optionalUser.orElseGet(() -> {
            User newUser = new User();
            newUser.setKakaoId(kakaoId);
            newUser.setUsername(nickname);
            newUser.setCreated_at(LocalDateTime.now());
            newUser.setUpdated_at(LocalDateTime.now());
            newUser.setUser_level(0);
            return userRepository.save(newUser);
        });

        int subscriptionCount = user.getSubscriptions().size();

        String jwtToken = jwtTokenProvider.createToken(user.getUserId().toString());

        HashMap<String, Object> result = new HashMap<>();
        result.put("userId", user.getUserId());
        result.put("jwtToken", jwtToken);
        result.put("subscriptionCount", subscriptionCount);

        return new CommonResponse<>(result, "로그인에 성공하였습니다.");
    }
}
