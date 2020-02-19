package com.loyaltypartner.elok.quiz.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.loyaltypartner.elok.quiz.model.Question;

@Repository
public interface QuestionRepository extends JpaRepository<Question, Long>, QuestionRepositoryCustom {
    
    public Page<Question> findByDomain(Long domainId, Pageable pageable);
    
    public List<Question> findByTitleOrText(String query);
    
    public Optional<Question> findByIdFetchDomain(Long questionId);
    
    public Optional<Question> findByIdFetchQuestions(Long questionId);
    
}