package com.loyaltypartner.elok.quiz.model;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.Enumerated;
import javax.persistence.FetchType;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Getter
@Setter
@NoArgsConstructor
public class Question extends BaseEntity {

    private String title;
    private String text;
    private String image;
    private String createdBy;

    @Enumerated
    private Difficulty diffculty;

    @ManyToOne(fetch = FetchType.LAZY)
    private Domain domain;

    @OneToMany(mappedBy = "question", cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    private List<Answer> answers;

    public Question(String title, String text, String image, String createdBy, Difficulty diffculty, Domain domain) {
        super();
        this.title = title;
        this.text = text;
        this.image = image;
        this.createdBy = createdBy;
        this.diffculty = diffculty;
        this.domain = domain;
    }

    public void addAnswer(Answer answer) {
        if (answers == null) {
            answers = new ArrayList<Answer>();
        }
        answers.add(answer);
    }

}
