package com.loyaltypartner.elok.quiz.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.loyaltypartner.elok.quiz.model.HighscoreEntry;

@Repository
public interface HighscoreEntryRepository extends JpaRepository<HighscoreEntry, Long> {

}
