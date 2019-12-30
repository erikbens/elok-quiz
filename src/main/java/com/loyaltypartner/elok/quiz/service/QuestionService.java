package com.loyaltypartner.elok.quiz.service;

import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.loyaltypartner.elok.quiz.controller.exception.QuestionNotFoundException;
import com.loyaltypartner.elok.quiz.model.Answer;
import com.loyaltypartner.elok.quiz.model.Question;
import com.loyaltypartner.elok.quiz.repository.QuestionRepository;

@Service
public class QuestionService {

    @Autowired
    private QuestionRepository questionRepository;

    public List<Question> findAll() {
        return questionRepository.findAll();
    }

    public Question findById(Long id) throws QuestionNotFoundException {
        Optional<Question> optional = questionRepository.findById(id);
        if (!optional.isPresent()) {
            throw new QuestionNotFoundException();
        }

        return optional.get();
    }

    public Boolean checkAnswers(Question question, List<Long> answers) {
        List<Answer> questionAnswers = question.getAnswers();
        List<Answer> correctAnswers = questionAnswers.stream().filter(a -> a.getCorrect() == true).collect(Collectors.toList());
        List<Answer> chosenAnswers = questionAnswers.stream().filter(a -> answers.stream().anyMatch(b -> b.equals(a.getId()))).collect(Collectors.toList());

        if (chosenAnswers.size() == correctAnswers.size()) {
            Collections.sort(chosenAnswers);
            Collections.sort(correctAnswers);
            if (chosenAnswers.equals(correctAnswers)) {
                return Boolean.TRUE;
            }
        }
        return Boolean.FALSE;
    }

}
