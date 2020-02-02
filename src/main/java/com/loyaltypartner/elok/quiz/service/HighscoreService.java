package com.loyaltypartner.elok.quiz.service;

import java.util.ArrayList;
import java.util.Optional;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.loyaltypartner.elok.quiz.model.Highscore;
import com.loyaltypartner.elok.quiz.model.HighscoreEntry;
import com.loyaltypartner.elok.quiz.model.QuestionFilter;
import com.loyaltypartner.elok.quiz.model.QuizResultDTO;
import com.loyaltypartner.elok.quiz.repository.HighscoreRepository;
import com.loyaltypartner.elok.quiz.repository.QuestionFilterRepository;

@Service
public class HighscoreService {
    
    @Autowired
    private HighscoreRepository highscoreRepository;
    
    @Autowired
    private QuestionFilterRepository questionFilterRepository;
    
    @Autowired
    private ModelMapper modelMapper;
    
    public Highscore findHighscoreByQuestionFilter(QuestionFilter questionFilter) {
        Optional<Highscore> queryResult = highscoreRepository.findHighscoreByQuestionFilter(questionFilter.getId());
        
        Highscore highscore;
        if (queryResult.isPresent()) {
            highscore = queryResult.get();
        } else {
            highscore = new Highscore();
            highscore.setQuestionFilter(questionFilter);
            highscore.setEntries(new ArrayList<HighscoreEntry>());
            highscore = highscoreRepository.save(highscore);
        }
        return highscore;
    }
    
    public Highscore addEntryForHighscoreId(Long highscoreId, QuizResultDTO model) {
        Optional<Highscore> queryResult = highscoreRepository.findById(highscoreId);
        if (queryResult.isPresent()) {
            Highscore highscore = queryResult.get();
            HighscoreEntry entry = modelMapper.map(model, HighscoreEntry.class);
            highscore.getEntries().add(entry);
            return highscoreRepository.save(highscore);
        }
        return null;
    }
    
    public Highscore deleteHighscoreEntryById(Long highscoreEntryId) {
        return null;
    }

    public Highscore handleQuizResult(QuizResultDTO quizResult) {
        QuestionFilter questionFilter = questionFilterRepository.findQuestionFilterForDomainIdAndDifficulty(quizResult.getDomainId(), quizResult.getDifficulty());
        if (questionFilter != null) {
            Highscore highscore = findHighscoreByQuestionFilter(questionFilter);
            return addEntryForHighscoreId(highscore.getId(), quizResult);
        }
        return null;
    }
    
}
