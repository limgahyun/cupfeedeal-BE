package com.cupfeedeal.domain.Cupcat.repository;

import com.cupfeedeal.domain.Cupcat.entity.Cupcat;
import com.cupfeedeal.domain.Cupcat.entity.UserCupcat;
import com.cupfeedeal.domain.User.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface UserCupcatRepository extends JpaRepository<UserCupcat, Long> {
    Optional<UserCupcat> findByUser(User user);
    Optional<UserCupcat> findByCupcat(Cupcat cupcat);

    @Query("select uc from UserCupcat uc where uc.user = :user AND uc.deletedAt is null ORDER BY uc.createdAt desc LIMIT 1")
    Optional<UserCupcat> findTop1ByUserOrderByCreatedAtDesc(@Param("user") User user);

    @Query("select uc from UserCupcat uc where uc.user = :user AND uc.cupcat.level = 5 AND uc.deletedAt is null ORDER BY uc.createdAt asc")
    List<UserCupcat> findAllByUserOrderByCreatedAtAsc(@Param("user") User user);
}
