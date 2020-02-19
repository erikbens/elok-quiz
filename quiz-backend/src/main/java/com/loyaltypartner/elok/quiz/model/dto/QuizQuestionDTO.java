package com.loyaltypartner.elok.quiz.model.dto;

import java.util.List;

import com.loyaltypartner.elok.quiz.model.Difficulty;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@Getter
@Setter
public class QuizQuestionDTO {

    private Long id;
    private String title;
    private String text;
    private String image;
    private Difficulty difficulty;
    
    private List<QuizAnswerDTO> answers;    
}
