package com.loyaltypartner.elok.quiz.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;

import com.loyaltypartner.elok.quiz.controller.exception.DomainNotFoundException;
import com.loyaltypartner.elok.quiz.model.Domain;
import com.loyaltypartner.elok.quiz.service.DomainService;

@RestController
public class DomainController implements IDomainController {

    @Autowired
    private DomainService domainService;

    @Override
    public ResponseEntity<List<Domain>> findAll() {
        return new ResponseEntity<List<Domain>>(domainService.findAll(), HttpStatus.OK);
    }

    @Override
    public ResponseEntity<Domain> findById(Long domainId) {
        try {
            return new ResponseEntity<Domain>(domainService.findById(domainId), HttpStatus.OK);
        } catch (DomainNotFoundException e) {
            return new ResponseEntity<Domain>(HttpStatus.NOT_FOUND);
        }
    }

}
