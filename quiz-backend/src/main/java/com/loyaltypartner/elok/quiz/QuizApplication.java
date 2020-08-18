package com.loyaltypartner.elok.quiz;

import java.util.Locale;

import org.modelmapper.ModelMapper;
import org.modelmapper.convention.MatchingStrategies;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.core.io.Resource;
import org.springframework.core.io.ResourceLoader;
import org.springframework.web.filter.CommonsRequestLoggingFilter;
import org.springframework.web.servlet.LocaleResolver;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
import org.springframework.web.servlet.i18n.AcceptHeaderLocaleResolver;

import com.fasterxml.jackson.databind.Module;
import com.fasterxml.jackson.datatype.hibernate5.Hibernate5Module;
import com.loyaltypartner.elok.quiz.controller.exception.UserNotUniqueException;
import com.loyaltypartner.elok.quiz.imports.XmlQuestionImporter;
import com.loyaltypartner.elok.quiz.imports.converter.XmlToModelConverter;
import com.loyaltypartner.elok.quiz.model.Answer;
import com.loyaltypartner.elok.quiz.model.Domain;
import com.loyaltypartner.elok.quiz.model.Role;
import com.loyaltypartner.elok.quiz.model.User;
import com.loyaltypartner.elok.quiz.model.dto.QuestionDTO;
import com.loyaltypartner.elok.quiz.service.AnswerService;
import com.loyaltypartner.elok.quiz.service.DomainService;
import com.loyaltypartner.elok.quiz.service.QuestionService;
import com.loyaltypartner.elok.quiz.service.UserService;
import com.loyaltypartner.elok.quiz.storage.FileSystemStorageService;
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
    public CommandLineRunner importFromXml(DomainService domainService, QuestionService questionService, AnswerService answerService,
            ResourceLoader resourceLoader, ModelMapper modelMapper) {
        return args -> {
            if (questionService.findAll().isEmpty()) {
                Resource resource = resourceLoader.getResource("classpath:question_pool_all.xml");
                List list = XmlQuestionImporter.readXml(resource.getInputStream());
                if (list != null) {
                    for (com.loyaltypartner.elok.quiz.xml.Question question : list.getQuestion()) {
                        String area = question.getArea();
                        Domain domain = domainService.findByName(area);
                        if (domain == null) {
                            domain = new Domain();
                            domain.setName(area);
                            domain = domainService.create(domain);
                        }
                        
                        QuestionDTO modelQuestion = modelMapper.map(XmlToModelConverter.toModelQuestion(question), QuestionDTO.class);
                        modelQuestion.setDomain(domain);
                        modelQuestion = modelMapper.map(questionService.createQuestion(modelQuestion), QuestionDTO.class);
                        
                        for (com.loyaltypartner.elok.quiz.xml.Answer answer : question.getAnswer()) {
                            Answer modelAnswer = XmlToModelConverter.toModelAnswer(answer);
                            answerService.createAnswerForQuestionId(modelQuestion.getId(), modelAnswer);
                        }
                        
                    }
                }
            }
        };
    }

    @Bean
    public CommandLineRunner initStorage(FileSystemStorageService storageService) {
        return args -> {
            storageService.init();
        };
    }

    @Bean
    public CommandLineRunner initAdminUser(UserService userService) {
        return args -> {
            try {
                User admin = new User("admin", "admin1#", Role.ADMIN);
                userService.create(admin);
            } catch (UserNotUniqueException e) {
                //ignore
            }
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

    @Bean
    public LocaleResolver localeResolver() {
        AcceptHeaderLocaleResolver localeResolver = new AcceptHeaderLocaleResolver();
        localeResolver.setDefaultLocale(Locale.GERMANY);
        return localeResolver;
    }

}
