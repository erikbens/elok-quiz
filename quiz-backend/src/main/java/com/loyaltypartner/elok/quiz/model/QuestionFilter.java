package com.loyaltypartner.elok.quiz.model;

import javax.persistence.Entity;
import javax.persistence.Enumerated;
import javax.persistence.JoinColumn;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToOne;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@Getter
@Setter
@Entity
@NamedQueries({
        @NamedQuery(name = "QuestionFilter.findQuestionFilterForDomainIdAndDifficulty", query = "SELECT f FROM QuestionFilter f WHERE ((:difficulty is null and f.difficulty is null) OR f.difficulty = :difficulty) AND ((:domainId is null and f.domainId is null) OR f.domainId = :domainId)") })
public class QuestionFilter extends BaseEntity {

    private Long domainId;

    @Enumerated
    private Difficulty difficulty;

    @OneToOne
    @JoinColumn
    @JsonIgnoreProperties("questionFilter")
    private Highscore highscore;

}
