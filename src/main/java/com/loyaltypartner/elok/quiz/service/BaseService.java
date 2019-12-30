package com.loyaltypartner.elok.quiz.service;

import java.util.List;

import com.loyaltypartner.elok.quiz.controller.exception.UserNotFoundException;

public interface BaseService<T, ID> {

    List<T> findAll();

    T findById(ID id) throws UserNotFoundException;

    void delete(T entity);

    T create(T entity);
    
    T update(ID id, T entity) throws UserNotFoundException;
}
