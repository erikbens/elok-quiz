package com.loyaltypartner.elok.quiz.model;

import java.util.List;

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
