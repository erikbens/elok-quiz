package com.loyaltypartner.elok.quiz.controller;

import java.util.List;
import java.util.stream.Collectors;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;

import com.loyaltypartner.elok.quiz.controller.exception.HighscoreNotFoundException;
import com.loyaltypartner.elok.quiz.i18n.Translator;
import com.loyaltypartner.elok.quiz.model.Difficulty;
import com.loyaltypartner.elok.quiz.model.Highscore;
import com.loyaltypartner.elok.quiz.model.Question;
import com.loyaltypartner.elok.quiz.model.QuestionFilter;
import com.loyaltypartner.elok.quiz.model.QuizQuestionDTO;
import com.loyaltypartner.elok.quiz.model.QuizResultDTO;
import com.loyaltypartner.elok.quiz.service.HighscoreService;
import com.loyaltypartner.elok.quiz.service.QuestionService;

@RestController
public class QuizController implements IQuizController {

    @Autowired
    private QuestionService questionService;
    
    @Autowired
    private HighscoreService highscoreService;
    
    @Autowired
    private ModelMapper modelMapper;

    @Override
    public ResponseEntity<List<QuizQuestionDTO>> getQuizByQuestionFilter(QuestionFilter questionFilter) {
        Long domainId = questionFilter != null ? questionFilter.getDomainId() : null;
        Difficulty difficulty = questionFilter != null ? questionFilter.getDifficulty() : null;
        List<Question> questions = questionService.findQuestionsByDomainIdAndDifficulty(domainId, difficulty);
        List<QuizQuestionDTO> quizQuestions = questions.stream().map(this::convertToDto).collect(Collectors.toList());
        return new ResponseEntity<List<QuizQuestionDTO>>(quizQuestions, HttpStatus.OK);
    }
    
    private QuizQuestionDTO convertToDto(Question question) {
        QuizQuestionDTO dto = modelMapper.map(question, QuizQuestionDTO.class);
        return dto;
    }

    @Override
    public ResponseEntity<Highscore> submitQuiz(QuizResultDTO quizResult) {
        try {
            return new ResponseEntity<Highscore>(highscoreService.handleQuizResult(quizResult), HttpStatus.OK);
        } catch (HighscoreNotFoundException e) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, Translator.toLocale("error.highscore.notfound"), e);
        }
    }
    
}
