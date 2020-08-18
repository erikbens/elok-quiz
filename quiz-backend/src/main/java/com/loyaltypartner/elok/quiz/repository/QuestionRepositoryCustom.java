package com.loyaltypartner.elok.quiz.repository;

import java.util.List;

import com.loyaltypartner.elok.quiz.model.Difficulty;
import com.loyaltypartner.elok.quiz.model.Question;

public interface QuestionRepositoryCustom {
    
    public List<Question> findQuestionsForDomainIdAndDifficulty(Long domainId, Difficulty difficulty);
       
}
