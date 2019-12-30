package com.loyaltypartner.elok.quiz.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.loyaltypartner.elok.quiz.model.User;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {

}
