package com.loyaltypartner.elok.quiz.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.loyaltypartner.elok.quiz.model.Question;

@Repository
public interface QuestionRepository extends JpaRepository<Question, Long> {

}
