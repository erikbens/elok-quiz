package com.loyaltypartner.elok.quiz.model;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.ManyToOne;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@Getter
@Setter
@Entity
public class HighscoreEntry extends BaseEntity {
    
    private String name;
    private Integer correctAnswers;
    private Integer incorrectAnswers;
    
    @JsonIgnoreProperties("entries")
    @ManyToOne(fetch = FetchType.EAGER)
    private Highscore highscore;

}
