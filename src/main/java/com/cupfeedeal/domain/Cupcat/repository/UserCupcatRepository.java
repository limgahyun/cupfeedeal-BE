package com.cupfeedeal.domain.Cupcat.repository;

import com.cupfeedeal.domain.Cupcat.entity.Cupcat;
import com.cupfeedeal.domain.Cupcat.entity.UserCupcat;
import com.cupfeedeal.domain.User.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface UserCupcatRepository extends JpaRepository<UserCupcat, Long> {
    Optional<UserCupcat> findByUser(User user);
    Optional<UserCupcat> findByCupcat(Cupcat cupcat);

    Optional<UserCupcat> findTop1ByUserOrderByCreatedAtDesc(User user);

    List<UserCupcat> findAllByUserOrderByCreatedAtAsc(User user);
}
