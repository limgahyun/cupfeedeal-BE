package com.cupfeedeal.domain.User.service;

import com.cupfeedeal.domain.User.entity.CustomUserdetails;
import com.cupfeedeal.domain.User.entity.User;
import com.cupfeedeal.domain.User.repository.UserRepository;
import com.cupfeedeal.global.exception.ApplicationException;
import com.cupfeedeal.global.exception.ExceptionCode;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
public class CustomUserDetailService implements UserDetailsService {

    private final UserRepository userRepository;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        if (username == null || username.isBlank()) {
            System.out.println("유효하지 않은 이름이 전달되었습니다: " + username);
            throw new UsernameNotFoundException("이름이 비어 있습니다.");
        }

        System.out.println("요청받은 이름: " + username);

        User user = userRepository.findByUsername(username)
                .orElseThrow(() -> {
                    System.out.println("해당 이름으로 사용자를 찾을 수 없습니다: " + username);
                    return new UsernameNotFoundException(ExceptionCode.USER_NOT_FOUND.getMessage());
                });

        return new CustomUserdetails(user);
    }

    public User loadUserByCustomUserDetails(CustomUserdetails customUserdetails) {
        if (customUserdetails == null) {
            throw new ApplicationException(ExceptionCode.USER_NOT_FOUND);
        }
        return customUserdetails.getUser();
    }
}
