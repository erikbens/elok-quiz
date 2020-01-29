package com.loyaltypartner.elok.quiz.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;

import com.loyaltypartner.elok.quiz.controller.exception.DomainNotFoundException;
import com.loyaltypartner.elok.quiz.controller.exception.QuestionNotFoundException;
import com.loyaltypartner.elok.quiz.model.Question;
import com.loyaltypartner.elok.quiz.service.QuestionService;

@RestController
public class QuestionController implements IQuestionController {

    @Autowired
    private QuestionService questionService;

    @Override
    public ResponseEntity<List<Question>> findAll(Integer pageSize, Integer pageNumber) {
        Pageable pageable = null;
        if (pageSize != null || pageNumber != null) {
            pageable = PageRequest.of(pageNumber, pageSize);
        }
        return new ResponseEntity<List<Question>>(questionService.findAll(pageable), HttpStatus.OK);
    }

    @Override
    public ResponseEntity<Question> findById(Long questionId, Boolean withDomain) {
        try {
            if (withDomain != null && withDomain) {
                return new ResponseEntity<Question>(questionService.findByIdFetchDomain(questionId), HttpStatus.OK);
            } else {
                return new ResponseEntity<Question>(questionService.findById(questionId), HttpStatus.OK);
            }
        } catch (QuestionNotFoundException e) {
            return new ResponseEntity<Question>(HttpStatus.NOT_FOUND);
        }
    }

    @Override
    public ResponseEntity<List<Question>> findByDomainId(Long domainId, Integer pageSize, Integer pageNumber) {
        Pageable pageable = null;
        if (pageSize != null || pageNumber != null) {
            pageable = PageRequest.of(pageNumber, pageSize);
        }
        return new ResponseEntity<List<Question>>(questionService.findQuestionsByDomain(domainId, pageable), HttpStatus.OK);
    }

    @Override
    public ResponseEntity<List<Question>> findByTitleOrText(String query) {
        return new ResponseEntity<List<Question>>(questionService.findQuestionsByTitleOrText(query), HttpStatus.OK);
    }

    @Override
    public ResponseEntity<Question> createQuestion(Question question) {
        return new ResponseEntity<Question>(questionService.createQuestion(question), HttpStatus.CREATED);
    }

    @Override
    public ResponseEntity<Question> updateQuestion(Long questionId, Question question) {
        try {
            if (question.getDomain() == null) {
                return new ResponseEntity<Question>(HttpStatus.BAD_REQUEST);
            }
            return new ResponseEntity<Question>(questionService.updateQuestion(questionId, question), HttpStatus.OK);
        } catch (QuestionNotFoundException e) {
            return new ResponseEntity<Question>(HttpStatus.NOT_FOUND);
        } catch (DomainNotFoundException e) {
            //TODO Correct error message
            return new ResponseEntity<Question>(HttpStatus.NOT_FOUND);
        }
    }

    @Override
    public ResponseEntity<Void> deleteQuestion(Long questionId) {
        try {
            questionService.deleteQuestion(questionId);
            return new ResponseEntity<Void>(HttpStatus.OK);
        } catch (QuestionNotFoundException e) {
            return new ResponseEntity<Void>(HttpStatus.NOT_FOUND);
        }
    }

    @Override
    public ResponseEntity<Boolean> checkAnswersForQuestionId(Long questionId, List<Long> answerIds) {
        try {
            return new ResponseEntity<Boolean>(questionService.checkAnswers(questionId, answerIds), HttpStatus.OK);
        } catch (QuestionNotFoundException e) {
            return new ResponseEntity<Boolean>(HttpStatus.NOT_FOUND);
        }
    }

}
