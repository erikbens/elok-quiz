package com.loyaltypartner.elok.quiz.service;

import java.util.ArrayList;
import java.util.Optional;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.loyaltypartner.elok.quiz.controller.exception.HighscoreNotFoundException;
import com.loyaltypartner.elok.quiz.model.Highscore;
import com.loyaltypartner.elok.quiz.model.HighscoreEntry;
import com.loyaltypartner.elok.quiz.model.QuestionFilter;
import com.loyaltypartner.elok.quiz.model.QuizResultDTO;
import com.loyaltypartner.elok.quiz.repository.HighscoreEntryRepository;
import com.loyaltypartner.elok.quiz.repository.HighscoreRepository;
import com.loyaltypartner.elok.quiz.repository.QuestionFilterRepository;

@Service
public class HighscoreService {
    
    @Autowired
    private HighscoreRepository highscoreRepository;
    
    @Autowired
    private HighscoreEntryRepository highscoreEntryRepository;
    
    @Autowired
    private QuestionFilterRepository questionFilterRepository;
    
    @Autowired
    private ModelMapper modelMapper;
    
    private Highscore findHighscoreByQuestionFilter(QuestionFilter questionFilter) throws HighscoreNotFoundException {
        Optional<Highscore> queryResult = highscoreRepository.findHighscoreByQuestionFilter(questionFilter.getId());
        
        Highscore highscore;
        if (queryResult.isPresent()) {
            highscore = queryResult.get();
        } else {
            throw new HighscoreNotFoundException(questionFilter.getDifficulty(), questionFilter.getDomainId());
        }
        return highscore;
    }
    
    private Highscore addEntryForHighscoreId(Highscore highscore, QuizResultDTO model) {
        HighscoreEntry entry = modelMapper.map(model, HighscoreEntry.class);
        entry.setHighscore(highscore);
        highscore.getEntries().add(entry);
        highscoreEntryRepository.save(entry);
        return highscore;
    }
    
    public Highscore deleteHighscoreEntryById(Long highscoreEntryId) {
        return null;
    }

    public Highscore handleQuizResult(QuizResultDTO quizResult) throws HighscoreNotFoundException {
        QuestionFilter questionFilter = questionFilterRepository.findQuestionFilterForDomainIdAndDifficulty(quizResult.getDomainId(), quizResult.getDifficulty());
        
        Highscore highscore;
        if (questionFilter == null) {
            highscore = createHighscore(quizResult);
        } else {
            highscore = findHighscoreByQuestionFilter(questionFilter);
        }
        return addEntryForHighscoreId(highscore, quizResult);
    }
    
    private Highscore createHighscore(QuizResultDTO quizResult) {
        Highscore highscore = new Highscore();
        highscore.setEntries(new ArrayList<HighscoreEntry>());
        highscore = highscoreRepository.save(highscore);
        
        QuestionFilter questionFilter = new QuestionFilter();
        questionFilter.setDifficulty(quizResult.getDifficulty());
        questionFilter.setDomainId(quizResult.getDomainId());
        questionFilter.setHighscore(highscore);
        questionFilter = questionFilterRepository.save(questionFilter);
        
        highscore.setQuestionFilter(questionFilter);
        return highscore;
    }
    
}
