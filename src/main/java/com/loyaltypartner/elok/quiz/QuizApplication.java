package com.loyaltypartner.elok.quiz;

import java.util.ArrayList;
import java.util.List;

import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.web.filter.CommonsRequestLoggingFilter;

import com.loyaltypartner.elok.quiz.model.Answer;
import com.loyaltypartner.elok.quiz.model.Domain;
import com.loyaltypartner.elok.quiz.model.Question;
import com.loyaltypartner.elok.quiz.repository.AnswerRepository;
import com.loyaltypartner.elok.quiz.repository.DomainRepository;
import com.loyaltypartner.elok.quiz.repository.QuestionRepository;
import com.loyaltypartner.elok.quiz.repository.UserRepository;
import com.loyaltypartner.elok.quiz.service.QuestionService;

import springfox.documentation.swagger2.annotations.EnableSwagger2;

@SpringBootApplication
@EnableSwagger2
public class QuizApplication {

    public static void main(String[] args) {
        SpringApplication.run(QuizApplication.class, args);
    }

    @Bean
    public CommonsRequestLoggingFilter logFilter() {
        CommonsRequestLoggingFilter filter = new CommonsRequestLoggingFilter();
        filter.setIncludeQueryString(true);
        filter.setIncludePayload(true);
        filter.setMaxPayloadLength(1000);
        filter.setIncludeHeaders(true);
        filter.setAfterMessagePrefix("REQUEST HANDLED: ");
        return filter;
    }

    @Bean
    public CommandLineRunner demoData(UserRepository userRepository, QuestionRepository questionRepository, AnswerRepository answerRepository,
            DomainRepository domainRepository, QuestionService questionService) {
        return args -> {
            userRepository.save(DummyGenerator.generateUser());
            userRepository.save(DummyGenerator.generateUser());

            Domain domain = DummyGenerator.generateDomain();
            Question question = DummyGenerator.generateQuestion(domain);
            domain.addQuestion(question);
            Answer a1 = DummyGenerator.generateAnswer(question);
            Answer a2 = DummyGenerator.generateAnswer(question);
            Answer a3 = DummyGenerator.generateAnswer(question);
            Answer a4 = DummyGenerator.generateAnswer(question);
            question.addAnswer(a1);
            question.addAnswer(a2);
            question.addAnswer(a3);
            question.addAnswer(a4);
            
            domain = domainRepository.save(domain);
            question = questionRepository.save(question);
            a1 = answerRepository.save(a1);
            a2 = answerRepository.save(a2);
            a3 = answerRepository.save(a3);
            a4 = answerRepository.save(a4);

            List<Long> answers = new ArrayList<Long>();
            answers.add(a1.getId());
            answers.add(a2.getId());
            answers.add(a3.getId());
            answers.add(a4.getId());
        };
    }

}
