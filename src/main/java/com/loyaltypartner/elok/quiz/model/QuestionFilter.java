package com.loyaltypartner.elok.quiz.model;

import javax.persistence.Entity;
import javax.persistence.Enumerated;
import javax.persistence.JoinColumn;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToOne;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@Getter
@Setter
@Entity
@NamedQueries({
    @NamedQuery(name = "QuestionFilter.findQuestionFilterForDomainIdAndDifficulty", query = "SELECT f FROM QuestionFilter f WHERE f.difficulty = :difficulty AND f.domainId = :domainId")
})
public class QuestionFilter extends BaseEntity {
    
    private Long domainId;
    
    @Enumerated
    private Difficulty difficulty;
    
    @OneToOne
    @JoinColumn
    private Highscore highscore;

}
