package com.loyaltypartner.elok.quiz.model;

import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@Getter
@Setter
@Entity
@NamedQueries({
    @NamedQuery(name = "findHighscoreByQuestionFilter", query = "SELECT h FROM Highscore h WHERE h.questionFilter.id = :id")
})
public class Highscore extends BaseEntity {
    
    @OneToOne(mappedBy = "highscore")
    private QuestionFilter questionFilter;
    
    @OneToMany(mappedBy = "highscore", cascade = { CascadeType.PERSIST, CascadeType.REMOVE }, fetch = FetchType.EAGER)
    private List<HighscoreEntry> entries;

}
