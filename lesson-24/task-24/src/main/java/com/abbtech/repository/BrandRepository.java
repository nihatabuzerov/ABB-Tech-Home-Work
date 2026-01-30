package com.abbtech.repository;

import com.abbtech.model.CarBrand;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface BrandRepository extends JpaRepository<CarBrand, Integer> {
}
