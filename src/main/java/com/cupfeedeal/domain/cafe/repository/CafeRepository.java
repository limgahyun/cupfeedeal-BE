package com.cupfeedeal.domain.cafe.repository;

import com.cupfeedeal.domain.cafe.entity.Cafe;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CafeRepository extends JpaRepository<Cafe, Long> {
    List<Cafe> findTop3ByOrderByIdDesc();
}