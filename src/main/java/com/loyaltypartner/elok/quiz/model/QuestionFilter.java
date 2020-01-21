package com.loyaltypartner.elok.quiz.model;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@Getter
@Setter
public class QuestionFilter {
    
    private Long domainId;
    private Difficulty difficulty;

}
