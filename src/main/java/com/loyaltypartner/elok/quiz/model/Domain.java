package com.loyaltypartner.elok.quiz.model;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.OneToMany;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Getter
@Setter
@NoArgsConstructor
public class Domain extends BaseEntity {

    private String name;

    @OneToMany(mappedBy = "domain", cascade = { CascadeType.PERSIST, CascadeType.REMOVE }, fetch = FetchType.EAGER)
    private List<Question> questions;

    public Domain(String name) {
        super();
        this.name = name;
    }

    public void addQuestion(Question question) {
        if (questions == null) {
            questions = new ArrayList<Question>();
        }
        questions.add(question);
    }

}
