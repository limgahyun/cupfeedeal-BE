package com.cupfeedeal.domain.Auth.service;

import com.cupfeedeal.domain.Auth.dto.responseDto.KakaoTokenResponseDto;
import com.cupfeedeal.domain.Auth.dto.responseDto.KakaoUserInfoResponseDto;
import com.cupfeedeal.domain.User.entity.User;
import com.cupfeedeal.domain.User.repository.UserRepository;
import com.cupfeedeal.global.exception.ApplicationException;
import com.cupfeedeal.global.exception.ExceptionCode;
import io.netty.handler.codec.http.HttpHeaderValues;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatusCode;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

@Slf4j
@RequiredArgsConstructor
@Service
public class KakaoService {

    private String clientId;
    private final String KAUTH_TOKEN_URL_HOST;
    private final String KAUTH_USER_URL_HOST;
    private UserRepository userRepository;

    @Autowired
    public KakaoService(@Value("${spring.kakao.client_id}") String clientId, UserRepository userRepository) {
        this.clientId = clientId;
        KAUTH_TOKEN_URL_HOST ="https://kauth.kakao.com";
        KAUTH_USER_URL_HOST = "https://kapi.kakao.com";
        this.userRepository = userRepository;
    }

    public String getAccessTokenFromKakao(String code, String redirectUri) {
        KakaoTokenResponseDto kakaoTokenResponseDto = WebClient.create(KAUTH_TOKEN_URL_HOST).post()
                .uri(uriBuilder -> uriBuilder
                        .scheme("https")
                        .path("/oauth/token")
                        .queryParam("grant_type", "authorization_code")
                        .queryParam("client_id", clientId)
                        .queryParam("code", code)
                        .queryParam("redirect_uri", redirectUri)
                        .build(true))
                .header(HttpHeaders.CONTENT_TYPE, HttpHeaderValues.APPLICATION_X_WWW_FORM_URLENCODED.toString())
                .retrieve()
                .onStatus(HttpStatusCode::is4xxClientError, clientResponse ->
                        Mono.error(new RuntimeException(ExceptionCode.INVALID_VALUE_EXCEPTION.getMessage()))
                )
                .onStatus(HttpStatusCode::is5xxServerError, clientResponse ->
                        Mono.error(new RuntimeException(ExceptionCode.INTERNAL_SERVER_ERROR.getMessage()))
                )
                .bodyToMono(KakaoTokenResponseDto.class)
                .block();

//        log.info(" [Kakao Service] Access Token ------> {}", kakaoTokenResponseDto.getAccessToken());
//        log.info(" [Kakao Service] Refresh Token ------> {}", kakaoTokenResponseDto.getRefreshToken());
//        //제공 조건: OpenID Connect가 활성화 된 앱의 토큰 발급 요청인 경우 또는 scope에 openid를 포함한 추가 항목 동의 받기 요청을 거친 토큰 발급 요청인 경우
//        log.info(" [Kakao Service] Id Token ------> {}", kakaoTokenResponseDto.getIdToken());
//        log.info(" [Kakao Service] Scope ------> {}", kakaoTokenResponseDto.getScope());

        return kakaoTokenResponseDto.getAccessToken();
    }

    public KakaoUserInfoResponseDto getUserInfo(String accessToken) {

        KakaoUserInfoResponseDto userInfo = WebClient.create(KAUTH_USER_URL_HOST)
                .get()
                .uri(uriBuilder -> uriBuilder
                        .scheme("https")
                        .path("/v2/user/me")
                        .build(true))
                .header(HttpHeaders.AUTHORIZATION, "Bearer " + accessToken)
                .header(HttpHeaders.CONTENT_TYPE, HttpHeaderValues.APPLICATION_X_WWW_FORM_URLENCODED.toString())
                .retrieve()
                .onStatus(HttpStatusCode::is4xxClientError, clientResponse ->
                        Mono.error(new RuntimeException(ExceptionCode.INVALID_VALUE_EXCEPTION.getMessage()))
                )
                .onStatus(HttpStatusCode::is5xxServerError, clientResponse ->
                        Mono.error(new RuntimeException(ExceptionCode.INTERNAL_SERVER_ERROR.getMessage()))
                )
                .bodyToMono(KakaoUserInfoResponseDto.class)
                .block();

//        log.info("[ Kakao Service ] Auth ID ---> {} ", userInfo.getId());
//        log.info("[ Kakao Service ] NickName ---> {} ", userInfo.getKakaoAccount().getProfile().getNickname());

        return userInfo;
    }

    public void unlinkKakaoAccount(Long userId, HttpServletRequest request) {
        String kakaoUnlinkUrl = "https://kapi.kakao.com/v1/user/unlink";

        try{
            String authorizationHeader = request.getHeader(HttpHeaders.AUTHORIZATION);

            if (authorizationHeader == null || !authorizationHeader.startsWith("Bearer ")) {
                log.error("유효한 카카오 Access Token이 없습니다 - userId: {}", userId);
                return;
            }

            String accessToken = authorizationHeader.substring(7);

            String response = WebClient.create(KAUTH_USER_URL_HOST)
                    .post()
                    .uri(kakaoUnlinkUrl)
                    .header(HttpHeaders.AUTHORIZATION, "Bearer " + accessToken) // ✅ 올바른 카카오 Access Token 사용
                    .retrieve()
                    .bodyToMono(String.class)
                    .block();

        } catch (Exception e){
            log.error("카카오 연결 끊기 실패 - userId: {}", userId, e);
        }
    }



}
