package com.loyaltypartner.elok.quiz.model.dto;

import com.loyaltypartner.elok.quiz.model.Difficulty;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@Getter
@Setter
public class QuizResultDTO {
    
    private String name;
    private Integer correctAnswers;
    private Integer incorrectAnswers;
    
    private Long domainId;
    private Difficulty difficulty;

}
