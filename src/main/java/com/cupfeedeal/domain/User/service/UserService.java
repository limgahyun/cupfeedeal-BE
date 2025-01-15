package com.cupfeedeal.domain.User.service;

import com.cupfeedeal.domain.Cupcat.entity.UserCupcat;
import com.cupfeedeal.domain.Cupcat.repository.UserCupcatRepository;
import com.cupfeedeal.domain.User.dto.request.UserInfoUpdateRequestDto;
import com.cupfeedeal.domain.User.dto.response.UserInfoResponseDto;
import com.cupfeedeal.domain.User.entity.User;
import com.cupfeedeal.domain.User.repository.UserRepository;
import com.cupfeedeal.global.common.response.CommonResponse;
import com.cupfeedeal.global.exception.ExceptionCode;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class UserService {
    @Autowired
    private final UserRepository userRepository;
    @Autowired
    private final UserCupcatRepository userCupcatRepository;

    public CommonResponse<UserInfoResponseDto> getUserInfo(Long userId) {
        Optional<User> optionalUser = userRepository.findById(userId);
        if(optionalUser.isEmpty()) {
            throw new UsernameNotFoundException(ExceptionCode.USER_NOT_FOUND.getMessage());
        }

        User user = optionalUser.get();

        UserCupcat userCupcat = userCupcatRepository.findByUser(user);
        String cupcatImageUrl = userCupcat != null ? userCupcat.getCupcat().getImageUrl() : null;

        UserInfoResponseDto userInfoResponseDto = new UserInfoResponseDto(user.getUsername(), user.getUser_level(), cupcatImageUrl);

        return new CommonResponse<>(userInfoResponseDto, "유저 정볼르 성공적으로 조회했습니다.");
    }

    public CommonResponse<String> updateUserInfo(Long userId, String newUsername) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new UsernameNotFoundException(ExceptionCode.USER_NOT_FOUND.getMessage()));
        user.setUsername(newUsername);
        userRepository.save(user);

        return new CommonResponse<>("유저 정보를 성공적으로 수정했습니다.");
    }
}
