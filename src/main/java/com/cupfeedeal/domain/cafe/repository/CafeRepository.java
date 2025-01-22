package com.cupfeedeal.domain.cafe.repository;

import com.cupfeedeal.domain.User.entity.User;
import com.cupfeedeal.domain.cafe.entity.Cafe;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface CafeRepository extends JpaRepository<Cafe, Long> {
    List<Cafe> findTop7ByIsRecommendedIsTrueOrderByCreatedAtDesc();
    List<Cafe> findTop7ByIsNewOpenIsTrueOrderByCreatedAtDesc();

    @Query("SELECT c FROM Cafe c WHERE c.name LIKE %:name% and c.deletedAt is null")
    List<Cafe> findByNameContaining(@Param("name") String name);

    @Query("""
        SELECT c 
        FROM Cafe c 
        JOIN UserCafeLike cl ON cl.cafe = c
        WHERE c.name LIKE %:name% 
            and cl.user = :user
            and c.deletedAt is null""")
    List<Cafe> findByNameContainingAndUserLike(@Param("name") String name, @Param("user") User user);

    Optional<Cafe> findById(Long Id);

}