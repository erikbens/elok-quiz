package com.loyaltypartner.elok.quiz.model;

import javax.persistence.Entity;
import javax.persistence.Enumerated;
import javax.persistence.OneToOne;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@Getter
@Setter
@Entity
public class QuestionFilter extends BaseEntity {
    
    private Long domainId;
    
    @Enumerated
    private Difficulty difficulty;
    
    @OneToOne
    private Highscore highscore;

}
