package com.cupfeedeal.domain.Cupcat.repository;

import com.cupfeedeal.domain.Cupcat.entity.Cupcat;
import com.cupfeedeal.domain.Cupcat.entity.UserCupcat;
import com.cupfeedeal.domain.User.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserCupcatRepository extends JpaRepository<UserCupcat, Long> {
    Optional<UserCupcat> findByUser(User user);
    Optional<UserCupcat> findByCupcat(Cupcat cupcat);

    // bug: No property 'and' found for type 'User'; Traversed path: UserCupcat.user
    Optional<UserCupcat> findTop1ByUserAndOrderByCreatedAtAsc(User user);
}
