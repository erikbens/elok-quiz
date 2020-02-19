package com.loyaltypartner.elok.quiz.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.ManyToOne;

import com.fasterxml.jackson.annotation.JsonIgnore;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Getter
@Setter
@NoArgsConstructor
public class Answer extends BaseEntity implements Comparable<Answer> {
    
    @Column(length = 500)
    private String text;
    private Boolean correct;
    
    @JsonIgnore
    @ManyToOne(fetch = FetchType.LAZY)
    private Question question;

    public Answer(String text, Boolean correct, Question question) {
        super();
        this.text = text;
        this.correct = correct;
        this.question = question;
    }

    @Override
    public int compareTo(Answer other) {
        return this.getId().compareTo(other.getId());
    }

}
