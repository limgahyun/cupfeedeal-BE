package com.cupfeedeal.domain.Cupcat.service;

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
    private UserCupcatRepository userCupcatRepository;

    public UserCupcat findUserCupcatByUser(User user) {
        return userCupcatRepository.findTop1ByUserAndOrderByCreatedAtAsc(user)
                .orElse(null);
    }
}
