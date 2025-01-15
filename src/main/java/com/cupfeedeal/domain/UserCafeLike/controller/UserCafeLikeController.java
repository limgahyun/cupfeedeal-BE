package com.cupfeedeal.domain.UserCafeLike.controller;

import com.cupfeedeal.domain.UserCafeLike.dto.request.UserCafeLikeRequestDto;
import com.cupfeedeal.domain.UserCafeLike.service.UserCafeLikeService;
import com.cupfeedeal.global.common.response.CommonResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/like")
public class UserCafeLikeController {

    @Autowired
    private UserCafeLikeService userCafeLikeService;

    @PostMapping("/like")
    public ResponseEntity<CommonResponse<String>> addCafeLike(@RequestBody UserCafeLikeRequestDto userCafeLikeRequestDto) {
        CommonResponse<String> response = userCafeLikeService.addCafeLike(userCafeLikeRequestDto.getUserId(), userCafeLikeRequestDto.getCafeId());
        return ResponseEntity.ok(response);
    }

    @DeleteMapping("/like")
    public ResponseEntity<CommonResponse<String>> deleteCafeLike(@RequestBody UserCafeLikeRequestDto userCafeLikeRequestDto) {
        CommonResponse<String> response = userCafeLikeService.deleteCafeLike(userCafeLikeRequestDto.getUserId(), userCafeLikeRequestDto.getCafeId());
        return ResponseEntity.ok(response);
    }
}
