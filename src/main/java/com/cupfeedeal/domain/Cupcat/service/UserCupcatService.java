package com.cupfeedeal.domain.Cupcat.service;

import com.cupfeedeal.domain.Cupcat.entity.Cupcat;
import com.cupfeedeal.domain.Cupcat.entity.UserCupcat;
import com.cupfeedeal.domain.Cupcat.repository.UserCupcatRepository;
import com.cupfeedeal.domain.User.entity.User;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class UserCupcatService {
    private  final UserCupcatRepository userCupcatRepository;

    public UserCupcat findUserCupcatByUser(User user) {
        return userCupcatRepository.findTop1ByUserOrderByCreatedAtDesc(user)
                .orElse(null);
    }

    @Transactional
    public void createUserCupcat(User user, Cupcat cupcat, String cafeName) {
        UserCupcat userCupcat = UserCupcat.builder()
                .user(user)
                .cupcat(cupcat)
                .cafeName(cafeName)
                .build();
        userCupcatRepository.save(userCupcat);
    }
}
