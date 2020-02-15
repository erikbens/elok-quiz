package com.loyaltypartner.elok.quiz.model.dto;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.loyaltypartner.elok.quiz.model.Answer;
import com.loyaltypartner.elok.quiz.model.Difficulty;
import com.loyaltypartner.elok.quiz.model.Domain;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@Setter
@Getter
public class QuestionDTO {
    
    private Long id;
    private String title;
    private String text;
    private String image;
    private String createdBy;
    private Difficulty difficulty;
    @JsonIgnoreProperties("questions")
    private Domain domain;
    private List<Answer> answers;

}
