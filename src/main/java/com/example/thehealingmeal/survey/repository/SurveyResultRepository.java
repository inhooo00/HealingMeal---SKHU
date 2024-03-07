package com.example.thehealingmeal.survey.repository;


import com.example.thehealingmeal.member.domain.User;
import com.example.thehealingmeal.survey.domain.SurveyResult;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface SurveyResultRepository extends JpaRepository<SurveyResult, Long> {
    Optional<SurveyResult> findSurveyResultByUser(User user);

    SurveyResult findByUserId(Long userId);
}
