package com.loyaltypartner.elok.quiz.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.loyaltypartner.elok.quiz.controller.exception.AnswerNotFoundException;
import com.loyaltypartner.elok.quiz.controller.exception.QuestionNotFoundException;
import com.loyaltypartner.elok.quiz.model.Answer;
import com.loyaltypartner.elok.quiz.model.Question;
import com.loyaltypartner.elok.quiz.repository.AnswerRepository;
import com.loyaltypartner.elok.quiz.repository.QuestionRepository;

@Service
public class AnswerService {
    
    @Autowired
    private AnswerRepository answerRepository;
    
    @Autowired
    private QuestionRepository questionRepository;
    
    public List<Answer> getAnswersByQuestionId(Long questionId) throws QuestionNotFoundException {
        Optional<Question> questionResult = questionRepository.findByIdFetchQuestions(questionId);
        if (questionResult.isPresent()) {
            return questionResult.get().getAnswers();
        } else {
            throw new QuestionNotFoundException();
        }
    }
    
    public Answer findAnswerById(Long answerId) throws AnswerNotFoundException {
        Optional<Answer> answerResult = answerRepository.findById(answerId);
        if (answerResult.isPresent()) {
            return answerResult.get();
        } else {
            throw new AnswerNotFoundException();
        }
    }
    
    public Answer createAnswerForQuestionId(Long questionId, Answer model) throws QuestionNotFoundException {
        Optional<Question> questionResult = questionRepository.findByIdFetchQuestions(questionId);
        
        if (!questionResult.isPresent()) {
            throw new QuestionNotFoundException();
        }
        
        if (model.getId() != null) {
            model.setId(null);
        }
        
        Question question = questionResult.get();
        model.setQuestion(question);
        question.addAnswer(model);
        return answerRepository.save(model);
    }
    
    public Answer updateAnswer(Long answerId, Answer model) throws AnswerNotFoundException {
        Answer answer = findAnswerById(answerId);
        
        answer.setCorrect(model.getCorrect());
        answer.setText(model.getText());
        return answerRepository.save(answer);
    }
    
    public void deleteAnswer(Long answerId) {
        answerRepository.deleteById(answerId);
    }

}
