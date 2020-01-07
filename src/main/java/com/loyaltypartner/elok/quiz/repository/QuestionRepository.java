package com.loyaltypartner.elok.quiz.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.loyaltypartner.elok.quiz.model.Question;

@Repository
public interface QuestionRepository extends JpaRepository<Question, Long> {
    
    public List<Question> findByDomain(Long domainId);
    
    public List<Question> findByTitleOrText(String query);
    
    public Optional<Question> findByIdFetchDomain(Long questionId);

}
