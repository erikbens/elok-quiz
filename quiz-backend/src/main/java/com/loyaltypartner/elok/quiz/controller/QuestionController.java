package com.loyaltypartner.elok.quiz.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.server.ResponseStatusException;

import com.loyaltypartner.elok.quiz.controller.exception.DomainNotFoundException;
import com.loyaltypartner.elok.quiz.controller.exception.QuestionNotFoundException;
import com.loyaltypartner.elok.quiz.i18n.Translator;
import com.loyaltypartner.elok.quiz.model.Question;
import com.loyaltypartner.elok.quiz.model.dto.QuestionDTO;
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
    public ResponseEntity<Question> findById(Long questionId, Boolean withDomain) {
        try {
            if (withDomain != null && withDomain) {
                return new ResponseEntity<Question>(questionService.findByIdFetchDomain(questionId), HttpStatus.OK);
            } else {
                return new ResponseEntity<Question>(questionService.findById(questionId), HttpStatus.OK);
            }
        } catch (QuestionNotFoundException e) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, Translator.toLocale("error.question.notfound"), e);
        }
    }

    @Override
    public ResponseEntity<List<Question>> findByDomainId(Long domainId) {
        return new ResponseEntity<List<Question>>(questionService.findQuestionsByDomain(domainId), HttpStatus.OK);
    }

    @Override
    public ResponseEntity<List<Question>> findByTitleOrText(String query) {
        return new ResponseEntity<List<Question>>(questionService.findQuestionsByTitleOrText(query), HttpStatus.OK);
    }

    @Override
    public ResponseEntity<Question> createQuestion(QuestionDTO question, MultipartFile image) {
        return new ResponseEntity<Question>(questionService.createQuestion(question, image), HttpStatus.CREATED);
    }

    @Override
    public ResponseEntity<Question> updateQuestion(Long questionId, QuestionDTO question, MultipartFile image) {
        try {
            if (question.getDomain() == null) {
                return new ResponseEntity<Question>(HttpStatus.BAD_REQUEST);
            }
            return new ResponseEntity<Question>(questionService.updateQuestion(questionId, question, image), HttpStatus.OK);
        } catch (QuestionNotFoundException e) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, Translator.toLocale("error.question.notfound"), e);
        } catch (DomainNotFoundException e) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, Translator.toLocale("error.domain.notfound"), e);
        }
    }

    @Override
    public ResponseEntity<Void> deleteQuestion(Long questionId) {
        try {
            questionService.deleteQuestion(questionId);
            return new ResponseEntity<Void>(HttpStatus.OK);
        } catch (QuestionNotFoundException e) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, Translator.toLocale("error.question.notfound"), e);
        }
    }
    
    @Override
    public ResponseEntity<Void> removeQuestionFromDomain(Long questionId, Long domainId) {
        try {
            questionService.removeQuestionFromDomain(questionId, domainId);
            return new ResponseEntity<Void>(HttpStatus.OK);
        } catch (QuestionNotFoundException e) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, Translator.toLocale("error.question.notfound"), e);
        }
    }

    @Override
    public ResponseEntity<Boolean> checkAnswersForQuestionId(Long questionId, List<Long> answerIds) {
        try {
            return new ResponseEntity<Boolean>(questionService.checkAnswers(questionId, answerIds), HttpStatus.OK);
        } catch (QuestionNotFoundException e) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, Translator.toLocale("error.question.notfound"), e);
        }
    }

    @Override
    public ResponseEntity<Resource> findImageByQuestionId(Long questionId) {
        try {
            return new ResponseEntity<Resource>(questionService.findImageForQuestionId(questionId), HttpStatus.OK);
        } catch (QuestionNotFoundException e) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, Translator.toLocale("error.question.notfound"), e);
        }
    }

}
