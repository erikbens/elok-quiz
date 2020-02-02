package com.loyaltypartner.elok.quiz.controller;

import java.util.List;
import java.util.Locale;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;

import com.loyaltypartner.elok.quiz.controller.exception.DomainNotFoundException;
import com.loyaltypartner.elok.quiz.controller.exception.QuestionNotFoundException;
import com.loyaltypartner.elok.quiz.model.Question;
import com.loyaltypartner.elok.quiz.service.QuestionService;

@RestController
public class QuestionController implements IQuestionController {
    
    @Autowired
    private MessageSource messageSource;

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
            throw new ResponseStatusException(HttpStatus.NOT_FOUND,
                    messageSource.getMessage("error.question.notfound", null, Locale.GERMANY), e);
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
            throw new ResponseStatusException(HttpStatus.NOT_FOUND,
                    messageSource.getMessage("error.question.notfound", null, Locale.GERMANY), e);
        } catch (DomainNotFoundException e) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND,
                    messageSource.getMessage("error.domain.notfound", null, Locale.GERMANY), e);
        }
    }

    @Override
    public ResponseEntity<Void> deleteQuestion(Long questionId) {
        try {
            questionService.deleteQuestion(questionId);
            return new ResponseEntity<Void>(HttpStatus.OK);
        } catch (QuestionNotFoundException e) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND,
                    messageSource.getMessage("error.question.notfound", null, Locale.GERMANY), e);
        }
    }

    @Override
    public ResponseEntity<Boolean> checkAnswersForQuestionId(Long questionId, List<Long> answerIds) {
        try {
            return new ResponseEntity<Boolean>(questionService.checkAnswers(questionId, answerIds), HttpStatus.OK);
        } catch (QuestionNotFoundException e) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND,
                    messageSource.getMessage("error.question.notfound", null, Locale.GERMANY), e);
        }
    }

}
