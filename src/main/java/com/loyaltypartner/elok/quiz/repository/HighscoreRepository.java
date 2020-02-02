package com.loyaltypartner.elok.quiz.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.loyaltypartner.elok.quiz.model.Highscore;

@Repository
public interface HighscoreRepository extends JpaRepository<Highscore, Long> {
    
    public Optional<Highscore> findHighscoreByQuestionFilter(Long questionFilterId);

}
