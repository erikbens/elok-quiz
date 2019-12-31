package com.loyaltypartner.elok.quiz.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.loyaltypartner.elok.quiz.controller.exception.DomainNotFoundException;
import com.loyaltypartner.elok.quiz.model.Domain;
import com.loyaltypartner.elok.quiz.repository.DomainRepository;

@Service
public class DomainService {

    @Autowired
    private DomainRepository domainRepository;

    public List<Domain> findAll() {
        return domainRepository.findAll();
    }

    public Domain findById(Long id) throws DomainNotFoundException {
        Optional<Domain> optional = domainRepository.findById(id);
        if (!optional.isPresent()) {
            throw new DomainNotFoundException();
        }

        return optional.get();
    }
    
}
