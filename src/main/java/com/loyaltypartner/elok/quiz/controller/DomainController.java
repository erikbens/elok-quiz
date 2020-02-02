package com.loyaltypartner.elok.quiz.controller;

import java.util.List;
import java.util.Locale;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;

import com.loyaltypartner.elok.quiz.controller.exception.DomainNotFoundException;
import com.loyaltypartner.elok.quiz.model.Domain;
import com.loyaltypartner.elok.quiz.service.DomainService;

@RestController
public class DomainController implements IDomainController {
    
    @Autowired
    private MessageSource messageSource;

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
            throw new ResponseStatusException(HttpStatus.NOT_FOUND,
                    messageSource.getMessage("error.domain.notfound", null, Locale.GERMANY), e);
        }
    }

    @Override
    public ResponseEntity<Domain> createDomain(Domain domain) {
        return new ResponseEntity<Domain>(domainService.create(domain), HttpStatus.OK);
    }

    @Override
    public ResponseEntity<Domain> updateDomain(Long domainId, Domain domain) {
        try {
            return new ResponseEntity<Domain>(domainService.update(domainId, domain), HttpStatus.OK);
        } catch (DomainNotFoundException e) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND,
                    messageSource.getMessage("error.domain.notfound", null, Locale.GERMANY), e);
        }
    }

}
