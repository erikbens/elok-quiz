package com.loyaltypartner.elok.quiz;

import org.modelmapper.ModelMapper;
import org.modelmapper.convention.MatchingStrategies;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.web.filter.CommonsRequestLoggingFilter;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import com.fasterxml.jackson.databind.Module;
import com.fasterxml.jackson.datatype.hibernate5.Hibernate5Module;
import com.loyaltypartner.elok.quiz.imports.XmlQuestionImporter;
import com.loyaltypartner.elok.quiz.imports.converter.XmlToModelConverter;
import com.loyaltypartner.elok.quiz.model.Answer;
import com.loyaltypartner.elok.quiz.model.Domain;
import com.loyaltypartner.elok.quiz.model.Question;
import com.loyaltypartner.elok.quiz.repository.AnswerRepository;
import com.loyaltypartner.elok.quiz.repository.DomainRepository;
import com.loyaltypartner.elok.quiz.repository.QuestionRepository;
import com.loyaltypartner.elok.quiz.repository.UserRepository;
import com.loyaltypartner.elok.quiz.service.AnswerService;
import com.loyaltypartner.elok.quiz.service.DomainService;
import com.loyaltypartner.elok.quiz.service.QuestionService;
import com.loyaltypartner.elok.quiz.xml.List;

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
    public CommandLineRunner importFromXml(DomainService domainService, QuestionService questionService, AnswerService answerService) {
        return args -> {
            List list = XmlQuestionImporter.readXml("C:/Users/mysel/Downloads/question_pool_all.xml");
            if (list != null) {
                for (com.loyaltypartner.elok.quiz.xml.Question question : list.getQuestion()) {
                    String area = question.getArea();
                    Domain domain = domainService.findByName(area);
                    if (domain == null) {
                        domain = new Domain();
                        domain.setName(area);
                        domain = domainService.create(domain);
                    }
                    
                    Question modelQuestion = XmlToModelConverter.toModelQuestion(question);
                    modelQuestion.setDomain(domain);
                    modelQuestion = questionService.createQuestion(modelQuestion);
                    
                    for (com.loyaltypartner.elok.quiz.xml.Answer answer : question.getAnswer()) {
                        Answer modelAnswer = XmlToModelConverter.toModelAnswer(answer);
                        answerService.createAnswerForQuestionId(modelQuestion.getId(), modelAnswer);
                    }
                    
                }
            }
        };
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


            Question question1 = DummyGenerator.generateQuestion(domain);
            domain.addQuestion(question1);
            
            Answer a5 = DummyGenerator.generateAnswer(question1);
            Answer a6 = DummyGenerator.generateAnswer(question1);
            Answer a7 = DummyGenerator.generateAnswer(question1);
            question1.addAnswer(a5);
            question1.addAnswer(a6);
            question1.addAnswer(a7);
            
            domain = domainRepository.save(domain);
            a1 = answerRepository.save(a1);
            a2 = answerRepository.save(a2);
            a3 = answerRepository.save(a3);
            a4 = answerRepository.save(a4);
            answerRepository.save(a5);
            answerRepository.save(a6);
            answerRepository.save(a7);
        };
    }

    @Bean
    public WebMvcConfigurer corsConfigurer() {
        return new WebMvcConfigurer() {
            @Override
            public void addCorsMappings(CorsRegistry registry) {
                registry.addMapping("/**").allowedMethods("GET", "POST", "PUT", "DELETE", "OPTIONS").allowedOrigins("*").allowedHeaders("*");
            }
        };
    }
    
    @Bean
    protected Module module() {
        return new Hibernate5Module();
    }
    
    @Bean
    public ModelMapper modelMapper() {
        ModelMapper modelMapper = new ModelMapper();
        modelMapper.getConfiguration().setMatchingStrategy(MatchingStrategies.STRICT);
        return modelMapper;
    }

}
