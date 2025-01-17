package com.cupfeedeal.domain.Cupcat.repository;

import com.cupfeedeal.domain.Cupcat.entity.Cupcat;
import com.cupfeedeal.domain.Cupcat.entity.UserCupcat;
import com.cupfeedeal.domain.User.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface UserCupcatRepository extends JpaRepository<User, Long> {
    UserCupcat findByUserId(Long userId);

    //User userId(Long userId);
    //List<UserCupcat> findByCupcat(Cupcat cupcat);
}
