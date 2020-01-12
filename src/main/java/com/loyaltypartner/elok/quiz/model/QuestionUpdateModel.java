package com.loyaltypartner.elok.quiz.model;

import java.time.LocalDateTime;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class QuestionUpdateModel {
    
    private Long id;
    private String title;
    private String text;
    private String image;
    private Difficulty difficulty;
    private Long domainId;
    private LocalDateTime createdAt;
    private String createdBy;

}
