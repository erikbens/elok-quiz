package com.loyaltypartner.elok.quiz.controller;

import java.util.Collections;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;

import com.loyaltypartner.elok.quiz.model.Difficulty;
import com.loyaltypartner.elok.quiz.model.Question;
import com.loyaltypartner.elok.quiz.model.QuestionFilter;
import com.loyaltypartner.elok.quiz.service.QuestionService;

@RestController
public class QuizController implements IQuizController {

    @Autowired
    private QuestionService questionService;

    @Override
    public ResponseEntity<List<Question>> getQuizByQuestionFilter(QuestionFilter questionFilter) {
        Long domainId = questionFilter != null ? questionFilter.getDomainId() : null;
        Difficulty difficulty = questionFilter != null ? questionFilter.getDifficulty() : null;
        List<Question> questions = questionService.findQuestionsByDomainIdAndDifficulty(domainId, difficulty);
        Collections.shuffle(questions);
        return new ResponseEntity<List<Question>>(questions, HttpStatus.OK);
    }

}
