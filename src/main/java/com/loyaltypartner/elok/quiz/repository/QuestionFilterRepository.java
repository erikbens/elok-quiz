package com.loyaltypartner.elok.quiz.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.loyaltypartner.elok.quiz.model.Difficulty;
import com.loyaltypartner.elok.quiz.model.QuestionFilter;

@Repository
public interface QuestionFilterRepository extends JpaRepository<QuestionFilter, Long> {
    
    public QuestionFilter findQuestionFilterForDomainIdAndDifficulty(Long domainId, Difficulty difficulty);

}
