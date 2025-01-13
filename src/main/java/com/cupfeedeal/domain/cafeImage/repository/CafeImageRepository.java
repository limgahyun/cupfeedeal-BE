package com.cupfeedeal.domain.cafeImage.repository;

import com.cupfeedeal.domain.cafe.entity.Cafe;
import com.cupfeedeal.domain.cafeImage.entity.CafeImage;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface CafeImageRepository extends JpaRepository<CafeImage, Long> {
    List<CafeImage> findAllByCafeId(Long cafeId);
    Optional<CafeImage> findByCafeAndIsMainImageIsTrue(Cafe cafe);
}