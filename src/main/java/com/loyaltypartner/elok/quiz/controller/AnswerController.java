package com.loyaltypartner.elok.quiz.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;

import com.loyaltypartner.elok.quiz.controller.exception.AnswerNotFoundException;
import com.loyaltypartner.elok.quiz.controller.exception.QuestionNotFoundException;
import com.loyaltypartner.elok.quiz.model.Answer;
import com.loyaltypartner.elok.quiz.service.AnswerService;

@RestController
public class AnswerController implements IAnswerController {

    @Autowired
    private AnswerService answerService;

    @Override
    public ResponseEntity<Answer> findById(Long answerId) {
        try {
            return new ResponseEntity<Answer>(answerService.findAnswerById(answerId), HttpStatus.OK);
        } catch (AnswerNotFoundException e) {
            return new ResponseEntity<Answer>(HttpStatus.NOT_FOUND);
        }
    }

    @Override
    public ResponseEntity<List<Answer>> findAllByQuestionId(Long questionId) {
        try {
            return new ResponseEntity<List<Answer>>(answerService.getAnswersByQuestionId(questionId), HttpStatus.OK);
        } catch (QuestionNotFoundException e) {
            return new ResponseEntity<List<Answer>>(HttpStatus.NOT_FOUND);
        }
    }

    @Override
    public ResponseEntity<Answer> createAnswerForQuestionId(Long questionId, Answer answer) {
        try {
            return new ResponseEntity<Answer>(answerService.createAnswerForQuestionId(questionId, answer), HttpStatus.OK);
        } catch (QuestionNotFoundException e) {
            return new ResponseEntity<Answer>(HttpStatus.NOT_FOUND);
        }
    }

    @Override
    public ResponseEntity<Answer> updateAnswer(Long answerId, Answer answer) {
        try {
            return new ResponseEntity<Answer>(answerService.updateAnswer(answerId, answer), HttpStatus.OK);
        } catch (AnswerNotFoundException e) {
            return new ResponseEntity<Answer>(HttpStatus.NOT_FOUND);
        }
    }

    @Override
    public ResponseEntity<Void> deleteAnswer(Long answerId) {
        answerService.deleteAnswer(answerId);
        return new ResponseEntity<Void>(HttpStatus.OK);
    }

}
