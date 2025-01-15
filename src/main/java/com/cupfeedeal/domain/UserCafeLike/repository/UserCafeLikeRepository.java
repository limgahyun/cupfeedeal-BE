package com.cupfeedeal.domain.UserCafeLike.repository;

import com.cupfeedeal.domain.User.entity.User;
import com.cupfeedeal.domain.UserCafeLike.domain.UserCafeLike;
import com.cupfeedeal.domain.cafe.entity.Cafe;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserCafeLikeRepository extends JpaRepository<UserCafeLike, Long> {
    Optional<UserCafeLike> findByUserAndCafe(User user, Cafe cafe);
}
