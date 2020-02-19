package com.loyaltypartner.elok.quiz.repository;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;

import org.springframework.stereotype.Repository;

import com.loyaltypartner.elok.quiz.model.Difficulty;
import com.loyaltypartner.elok.quiz.model.Question;

@Repository
public class QuestionRepositoryImpl implements QuestionRepositoryCustom {

    @PersistenceContext
    private EntityManager entityManager;
    
    @Override
    public List<Question> findQuestionsForDomainIdAndDifficulty(Long domainId, Difficulty difficulty) {
        CriteriaBuilder cb = entityManager.getCriteriaBuilder();
        CriteriaQuery<Question> q = cb.createQuery(Question.class);
        Root<Question> question = q.from(Question.class);

        if (domainId != null) {
            q.where(cb.equal(question.get("domain").get("id"), domainId));
        }
        
        if (difficulty != null) {
            q.where(cb.equal(question.get("difficulty"), difficulty));
        }
        
        question.fetch("answers");
        return entityManager.createQuery(q.distinct(true)).getResultList();
    }

}
