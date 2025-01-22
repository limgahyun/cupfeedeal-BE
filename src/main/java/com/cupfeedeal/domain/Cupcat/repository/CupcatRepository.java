package com.cupfeedeal.domain.Cupcat.repository;

import com.cupfeedeal.domain.Cupcat.entity.Cupcat;
import com.cupfeedeal.domain.Cupcat.enumerate.CupcatTypeEnum;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CupcatRepository extends JpaRepository<Cupcat, Long> {
    Cupcat findByLevelAndType(Integer level, CupcatTypeEnum type);
}
