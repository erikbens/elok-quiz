package com.loyaltypartner.elok.quiz.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;

import com.loyaltypartner.elok.quiz.controller.exception.QuestionNotFoundException;
import com.loyaltypartner.elok.quiz.model.Question;
import com.loyaltypartner.elok.quiz.service.QuestionService;

@RestController
public class QuestionController implements IQuestionController {

    @Autowired
    private QuestionService questionService;

    @Override
    public ResponseEntity<List<Question>> findAll() {
        return new ResponseEntity<List<Question>>(questionService.findAll(), HttpStatus.OK);
    }

    @Override
    public ResponseEntity<Question> findById(Long questionId) {
        try {
            return new ResponseEntity<Question>(questionService.findById(questionId), HttpStatus.OK);
        } catch (QuestionNotFoundException e) {
            return new ResponseEntity<Question>(HttpStatus.NOT_FOUND);
        }
    }

    @Override
    public ResponseEntity<List<Question>> findByDomain(Long domainId) {
        return new ResponseEntity<List<Question>>(questionService.findQuestionsByDomain(domainId), HttpStatus.OK);
    }

}
