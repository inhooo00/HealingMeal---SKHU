package com.example.thehealingmeal.survey.dto;

import com.example.thehealingmeal.member.domain.User;
import com.example.thehealingmeal.survey.domain.FilterFood;
import com.example.thehealingmeal.survey.domain.SurveyResult;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class SurveyResultDto {
    private int Kcal;
    private float protein;
    private float carbohydrate;
    private float fat;

    @Builder
    public SurveyResultDto(int Kcal, float protein, float carbohydrate, float fat) {
        this.Kcal = Kcal;
        this.protein = protein;
        this.carbohydrate = carbohydrate;
        this.fat = fat;
    }

    // 정적 팩토리 메서드
    public static SurveyResultDto createSurveyResultDto(SurveyResult surveyResult) {
        return SurveyResultDto.builder()
                .Kcal(surveyResult.getKcal())
                .protein(surveyResult.getProtein())
                .carbohydrate(surveyResult.getCarbohydrate())
                .fat(surveyResult.getFat())
                .build();
    }
}
