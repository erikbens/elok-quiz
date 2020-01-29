package com.loyaltypartner.elok.quiz.model;

import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@Getter
@Setter
@Entity
public class Highscore extends BaseEntity {
    
    @OneToOne(mappedBy = "highscore")
    private QuestionFilter filter;
    
    @OneToMany(mappedBy = "highscore", cascade = { CascadeType.PERSIST, CascadeType.REMOVE }, fetch = FetchType.EAGER)
    private List<HighscoreEntry> entries;

}
