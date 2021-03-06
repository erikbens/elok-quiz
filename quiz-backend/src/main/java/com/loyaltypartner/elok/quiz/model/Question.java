package com.loyaltypartner.elok.quiz.model;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Enumerated;
import javax.persistence.FetchType;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Getter
@Setter
@NoArgsConstructor
@NamedQueries({
        @NamedQuery(name = "Question.findByTitleOrText", query = "SELECT q FROM Question q WHERE lower(q.title) LIKE lower(concat('%', :query, '%')) OR lower(q.text) LIKE lower(concat('%', :query, '%'))"),
        @NamedQuery(name = "Question.findByIdFetchDomain", query = "SELECT q FROM Question q JOIN FETCH q.domain WHERE q.id = :questionId"),
        @NamedQuery(name = "Question.findByIdFetchQuestions", query = "SELECT q FROM Question q LEFT JOIN FETCH q.answers WHERE q.id = :questionId") })
public class Question extends BaseEntity {

    private String title;

    @Column(length = 500)
    private String text;

    private String image;

    private String createdBy;

    @Enumerated
    private Difficulty difficulty;

    @JsonIgnoreProperties("questions")
    @ManyToOne(fetch = FetchType.EAGER)
    private Domain domain;

    @OneToMany(mappedBy = "question", cascade = { CascadeType.PERSIST, CascadeType.REMOVE }, fetch = FetchType.LAZY)
    private List<Answer> answers;

    public Question(String title, String text, String image, String createdBy, Difficulty diffculty, Domain domain) {
        super();
        this.title = title;
        this.text = text;
        this.image = image;
        this.createdBy = createdBy;
        this.difficulty = diffculty;
        this.domain = domain;
    }

    public void addAnswer(Answer answer) {
        if (answers == null) {
            answers = new ArrayList<Answer>();
        }
        answers.add(answer);
    }

}
