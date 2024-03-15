package com.example.thehealingmeal.survey.dto;


import com.example.thehealingmeal.member.domain.Gender;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class SurveyRequestDto {

    @NotBlank
    private String age;

    @NotBlank
    private Long diabetesType; // 당뇨유형

    @NotBlank
    private Long numberOfExercises; // 육체 활동 빈도

    @NotBlank
    private Long height;

    @NotBlank
    private Long weight;

    @NotBlank
    @Enumerated(EnumType.STRING)
    private Gender gender;

    @NotBlank
    private Long standardWeight; // 표준 체중

    @NotBlank
    private Long bodyMassIndex; // 체질량지수

    @NotBlank
    private Long caloriesNeededPerDay; // 하루 필요 열량

    @NotBlank
    private String weightLevel; // 현재 체중 단계
}
