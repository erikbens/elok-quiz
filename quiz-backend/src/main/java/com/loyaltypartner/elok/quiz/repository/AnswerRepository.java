package com.loyaltypartner.elok.quiz.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.loyaltypartner.elok.quiz.model.Answer;

@Repository
public interface AnswerRepository extends JpaRepository<Answer, Long> {

}
