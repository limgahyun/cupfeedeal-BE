package com.cupfeedeal.domain.UserCafeLike.controller;

import com.cupfeedeal.domain.User.entity.CustomUserdetails;
import com.cupfeedeal.domain.UserCafeLike.dto.request.UserCafeLikeRequestDto;
import com.cupfeedeal.domain.UserCafeLike.dto.response.UserCafeLikeResponseDto;
import com.cupfeedeal.domain.UserCafeLike.service.UserCafeLikeService;
import com.cupfeedeal.global.common.response.CommonResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/like")
public class UserCafeLikeController {

    @Autowired
    private UserCafeLikeService userCafeLikeService;

    @PostMapping("/like")
    public CommonResponse<UserCafeLikeResponseDto> addCafeLike(@AuthenticationPrincipal CustomUserdetails customUserdetails, @RequestBody UserCafeLikeRequestDto userCafeLikeRequestDto) {
        UserCafeLikeResponseDto userCafeLikeResponseDto = userCafeLikeService.addCafeLike(customUserdetails, userCafeLikeRequestDto);
        return new CommonResponse<>(userCafeLikeResponseDto, "카페 좋아요를 성공적으로 추가했습니다.");

    }

    @DeleteMapping("/like")
    public CommonResponse<String> deleteCafeLike(@AuthenticationPrincipal CustomUserdetails customUserdetails, @RequestBody UserCafeLikeRequestDto userCafeLikeRequestDto) {
        String message = userCafeLikeService.deleteCafeLike(customUserdetails, userCafeLikeRequestDto);
        return new CommonResponse<>(message);
    }
}
