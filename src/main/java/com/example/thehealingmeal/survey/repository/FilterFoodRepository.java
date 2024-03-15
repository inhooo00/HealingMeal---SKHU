package com.example.thehealingmeal.survey.repository;


import com.example.thehealingmeal.survey.domain.FilterFood;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface FilterFoodRepository extends JpaRepository<FilterFood, Long> {
    Optional<FilterFood> findFilterFoodByUserId(Long userId);
}
