package com.loyaltypartner.elok.quiz.service;

import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.Resource;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.loyaltypartner.elok.quiz.controller.exception.DomainNotFoundException;
import com.loyaltypartner.elok.quiz.controller.exception.QuestionNotFoundException;
import com.loyaltypartner.elok.quiz.model.Answer;
import com.loyaltypartner.elok.quiz.model.Difficulty;
import com.loyaltypartner.elok.quiz.model.Domain;
import com.loyaltypartner.elok.quiz.model.Question;
import com.loyaltypartner.elok.quiz.model.dto.QuestionDTO;
import com.loyaltypartner.elok.quiz.repository.DomainRepository;
import com.loyaltypartner.elok.quiz.repository.QuestionRepository;
import com.loyaltypartner.elok.quiz.storage.StorageService;

@Service
public class QuestionService {

    @Autowired
    private QuestionRepository questionRepository;

    @Autowired
    private DomainRepository domainRepository;

    @Autowired
    private ModelMapper modelMapper;

    @Autowired
    private StorageService storageService;

    @Value("${view.pagination.pagesize}")
    private Integer pageSize;

    public List<Question> findAll(Pageable pageable) {
        if (pageable == null) {
            pageable = PageRequest.of(0, this.pageSize);
        }
        return questionRepository.findAll(pageable).getContent();
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

    public List<Question> findQuestionsByDomain(Long domainId, Pageable pageable) {
        if (pageable == null) {
            pageable = PageRequest.of(0, this.pageSize);
        }
        return questionRepository.findByDomain(domainId, pageable).getContent();
    }

    public List<Question> findQuestionsByTitleOrText(String query) {
        return questionRepository.findByTitleOrText(query);
    }

    public Question createQuestion(QuestionDTO model) {
        return createQuestion(model, null);
    }

    public Question createQuestion(QuestionDTO model, MultipartFile image) {
        if (model.getId() != null) {
            model.setId(null);
        }
        if (model.getDomain() != null && model.getDomain().getId() == null) {
            model.setDomain(null);
        }

        Question question = modelMapper.map(model, Question.class);
        if (image != null) {
            storageService.store(image);
        }
        return questionRepository.save(question);
    }

    public Question updateQuestion(Long questionId, QuestionDTO model) throws QuestionNotFoundException, DomainNotFoundException {
        return updateQuestion(questionId, model, null);
    }

    public Question updateQuestion(Long questionId, QuestionDTO model, MultipartFile image) throws QuestionNotFoundException, DomainNotFoundException {
        Question question = findById(questionId);
        Domain domain = domainRepository.findById(model.getDomain().getId()).get();

        if (domain == null) {
            throw new DomainNotFoundException();
        }

        question.setDifficulty(model.getDifficulty());
        question.setImage(model.getImage());
        question.setDomain(domain);
        if (image != null) {
            storageService.store(image);
        }
        question.setText(model.getText());
        question.setTitle(model.getTitle());

        return questionRepository.save(question);
    }

    public Boolean checkAnswers(Long questionId, List<Long> answerIds) throws QuestionNotFoundException {
        Optional<Question> question = questionRepository.findByIdFetchQuestions(questionId);
        if (question.isPresent()) {
            List<Answer> questionAnswers = question.get().getAnswers();
            List<Answer> correctAnswers = questionAnswers.stream().filter(a -> a.getCorrect() == true).collect(Collectors.toList());
            List<Answer> chosenAnswers = questionAnswers.stream().filter(a -> answerIds.stream().anyMatch(b -> b.equals(a.getId())))
                    .collect(Collectors.toList());

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
        List<Question> questions = questionRepository.findQuestionsForDomainIdAndDifficulty(domainId, difficulty);
        Collections.shuffle(questions);
        for (Question question : questions) {
            Collections.shuffle(question.getAnswers());
        }
        return questions;
    }
    
    public Resource findImageForQuestionId(Long questionId) throws QuestionNotFoundException {
        Question question = findById(questionId);
        return storageService.loadAsResource(question.getImage());
    }

}
