package com.loyaltypartner.elok.quiz.service;

import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.loyaltypartner.elok.quiz.controller.exception.DomainNotFoundException;
import com.loyaltypartner.elok.quiz.controller.exception.QuestionNotFoundException;
import com.loyaltypartner.elok.quiz.model.Answer;
import com.loyaltypartner.elok.quiz.model.Difficulty;
import com.loyaltypartner.elok.quiz.model.Domain;
import com.loyaltypartner.elok.quiz.model.Question;
import com.loyaltypartner.elok.quiz.repository.DomainRepository;
import com.loyaltypartner.elok.quiz.repository.QuestionRepository;

@Service
public class QuestionService {

    @Autowired
    private QuestionRepository questionRepository;

    @Autowired
    private DomainRepository domainRepository;

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

    public Question findByIdFetchDomain(Long questionId) throws QuestionNotFoundException {
        Optional<Question> optional = questionRepository.findByIdFetchDomain(questionId);
        if (!optional.isPresent()) {
            throw new QuestionNotFoundException();
        }

        return optional.get();
    }

    public List<Question> findQuestionsByDomain(Long domainId) {
        return questionRepository.findByDomain(domainId);
    }

    public List<Question> findQuestionsByTitleOrText(String query) {
        return questionRepository.findByTitleOrText(query);
    }

    public Question createQuestion(Question question) {
        if (question.getId() != null) {
            question.setId(null);
        }
        if (question.getDomain() != null && question.getDomain().getId() == null) {
            question.setDomain(null);
        }
        return questionRepository.save(question);
    }

    public Question updateQuestion(Long questionId, Question model) throws QuestionNotFoundException, DomainNotFoundException {
        Question question = findById(questionId);
        Domain domain = domainRepository.findById(model.getDomain().getId()).get();

        if (domain == null) {
            throw new DomainNotFoundException();
        }

        question.setDifficulty(model.getDifficulty());
        question.setDomain(domain);
        question.setImage(model.getImage());
        question.setText(model.getText());
        question.setTitle(model.getTitle());

        return questionRepository.save(question);
    }

    public Boolean checkAnswers(Long questionId, List<Long> answerIds) throws QuestionNotFoundException {
        Optional<Question> question = questionRepository.findByIdFetchQuestions(questionId);
        if (question.isPresent()) {
            List<Answer> questionAnswers = question.get().getAnswers();
            List<Answer> correctAnswers = questionAnswers.stream().filter(a -> a.getCorrect() == true).collect(Collectors.toList());
            List<Answer> chosenAnswers = questionAnswers.stream().filter(a -> answerIds.stream().anyMatch(b -> b.equals(a.getId()))).collect(Collectors.toList());

            if (chosenAnswers.size() == correctAnswers.size()) {
                Collections.sort(chosenAnswers);
                Collections.sort(correctAnswers);
                if (chosenAnswers.equals(correctAnswers)) {
                    return Boolean.TRUE;
                }
            }
            return Boolean.FALSE;
        } else {
            throw new QuestionNotFoundException();
        }
    }

    public void deleteQuestion(Long questionId) throws QuestionNotFoundException {
        questionRepository.delete(findById(questionId));
    }

    public List<Question> findQuestionsByDomainIdAndDifficulty(Long domainId, Difficulty difficulty) {
        return questionRepository.findQuestionsForDomainIdAndDifficulty(domainId, difficulty);
    }

}
