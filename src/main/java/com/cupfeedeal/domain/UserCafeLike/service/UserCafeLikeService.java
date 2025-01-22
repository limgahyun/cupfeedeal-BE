package com.cupfeedeal.domain.UserCafeLike.service;

import com.cupfeedeal.domain.User.entity.CustomUserdetails;
import com.cupfeedeal.domain.User.entity.User;
import com.cupfeedeal.domain.User.repository.UserRepository;
import com.cupfeedeal.domain.UserCafeLike.dto.request.UserCafeLikeRequestDto;
import com.cupfeedeal.domain.UserCafeLike.dto.response.UserCafeLikeResponseDto;
import com.cupfeedeal.domain.UserCafeLike.entity.UserCafeLike;
import com.cupfeedeal.domain.UserCafeLike.repository.UserCafeLikeRepository;
import com.cupfeedeal.domain.cafe.entity.Cafe;
import com.cupfeedeal.domain.cafe.repository.CafeRepository;
import com.cupfeedeal.global.exception.ApplicationException;
import com.cupfeedeal.global.exception.ExceptionCode;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserCafeLikeService {

    @Autowired
    private UserCafeLikeRepository userCafeLikeRepository;
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private CafeRepository cafeRepository;

    public UserCafeLikeResponseDto addCafeLike(CustomUserdetails customUserdetails, UserCafeLikeRequestDto userCafeLikeRequestDto){

        User user = customUserdetails.getUser();

        Long cafeId = userCafeLikeRequestDto.getCafeId();
        Cafe cafe = cafeRepository.findById(cafeId)
                .orElseThrow(() -> new ApplicationException(ExceptionCode.NOT_FOUND_CAFE));

        //유저와 카페의 좋아요가 이미 존재하는 지 확인
        userCafeLikeRepository.findByUserAndCafe(user, cafe)
                .ifPresent(existingLike -> {
                    throw new ApplicationException(ExceptionCode.ALREADY_EXIST_LIKE);
                });

        UserCafeLike userCafeLike = UserCafeLike.builder()
                .user(user)
                .cafe(cafe)
                .build();

        userCafeLikeRepository.save(userCafeLike);

        return UserCafeLikeResponseDto.from(userCafeLike);
    }

    public String deleteCafeLike(CustomUserdetails customUserdetails, UserCafeLikeRequestDto userCafeLikeRequestDto){
        User user = customUserdetails.getUser();

        Long cafeId = userCafeLikeRequestDto.getCafeId();
        Cafe cafe = cafeRepository.findById(cafeId)
                .orElseThrow(() -> new ApplicationException(ExceptionCode.NOT_FOUND_CAFE));

        UserCafeLike userCafeLike = userCafeLikeRepository.findByUserAndCafe(user, cafe)
                .orElseThrow(() -> new ApplicationException(ExceptionCode.NOT_FOUND_CAFE_LIKE));

        userCafeLikeRepository.delete(userCafeLike);

        return "카페 좋아요를 성공적으로 삭제했습니다.";

    }
}
