package com.example.thehealingmeal.survey.repository;

import com.example.thehealingmeal.survey.domain.Survey;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface SurveyRepository extends JpaRepository<Survey,Long> {
    boolean existsSurveyByUserId(Long userId);
    Optional<Survey> findSurveyByUserId(Long userId);
    Optional<Survey> findByUserId(Long userId);
}
