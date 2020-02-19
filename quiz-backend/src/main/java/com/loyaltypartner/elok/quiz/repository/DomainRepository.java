package com.loyaltypartner.elok.quiz.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.loyaltypartner.elok.quiz.model.Domain;

@Repository
public interface DomainRepository extends JpaRepository<Domain, Long> {
    
    Domain findByName(String name);

}
