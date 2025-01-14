package com.cupfeedeal.domain.cafeSubscriptionType.repository;

import com.cupfeedeal.domain.cafeSubscriptionType.entity.CafeSubscriptionType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CafeSubscriptionTypeRepository extends JpaRepository<CafeSubscriptionType, Long> {
    List<CafeSubscriptionType> findAllByCafeId(Long cafeId);
}