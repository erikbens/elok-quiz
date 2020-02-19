package com.loyaltypartner.elok.quiz;

import static org.junit.jupiter.api.Assertions.*;

import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import com.loyaltypartner.elok.quiz.controller.exception.QuestionNotFoundException;
import com.loyaltypartner.elok.quiz.model.Answer;
import com.loyaltypartner.elok.quiz.model.Question;
import com.loyaltypartner.elok.quiz.service.QuestionService;

@SpringBootTest
public class QuestionServiceTest {
    
    @Autowired
    private QuestionService questionService;
    
    @Test
    public void testIncorrectAnswerCount() {
        Question question = new Question();
        question.setId(1L);
        
        Answer a1 = new Answer();
        a1.setId(1L);
        a1.setCorrect(false);
        
        Answer a2 = new Answer();
        a2.setId(2L);
        a2.setCorrect(true);
        
        Answer a3 = new Answer();
        a3.setId(3L);
        a3.setCorrect(false);
        
        Answer a4 = new Answer();
        a4.setId(4L);
        a4.setCorrect(true);
        
        question.addAnswer(a1);
        question.addAnswer(a2);
        question.addAnswer(a3);
        question.addAnswer(a4);
        
        List<Long> answers = new ArrayList<Long>();
        answers.add(a1.getId());
        answers.add(a2.getId());
        answers.add(a3.getId());
        answers.add(a4.getId());
        
        try {
            assertFalse(questionService.checkAnswers(question.getId(), answers));
        } catch (QuestionNotFoundException e) {
            e.printStackTrace();
        }
    }
    
    @Test
    public void testIncorrectAnswer() {
        Question question = new Question();
        question.setId(1L);
        
        Answer a1 = new Answer();
        a1.setId(1L);
        a1.setCorrect(false);
        
        Answer a2 = new Answer();
        a2.setId(2L);
        a2.setCorrect(true);
        
        Answer a3 = new Answer();
        a3.setId(3L);
        a3.setCorrect(false);
        
        Answer a4 = new Answer();
        a4.setId(4L);
        a4.setCorrect(true);
        
        question.addAnswer(a1);
        question.addAnswer(a2);
        question.addAnswer(a3);
        question.addAnswer(a4);
        
        List<Long> answers = new ArrayList<Long>();
        answers.add(a1.getId());
        answers.add(a4.getId());
        
        try {
            assertFalse(questionService.checkAnswers(question.getId(), answers));
        } catch (QuestionNotFoundException e) {
            e.printStackTrace();
        }
    }
    
    @Test
    public void testCorrectAnswer() {
        Question question = new Question();
        question.setId(1L);
        
        Answer a1 = new Answer();
        a1.setId(1L);
        a1.setCorrect(false);
        
        Answer a2 = new Answer();
        a2.setId(2L);
        a2.setCorrect(true);
        
        Answer a3 = new Answer();
        a3.setId(3L);
        a3.setCorrect(false);
        
        Answer a4 = new Answer();
        a4.setId(4L);
        a4.setCorrect(true);
        
        question.addAnswer(a1);
        question.addAnswer(a2);
        question.addAnswer(a3);
        question.addAnswer(a4);
        
        List<Long> answers = new ArrayList<Long>();
        answers.add(a2.getId());
        answers.add(a4.getId());
        
        try {
            assertTrue(questionService.checkAnswers(question.getId(), answers));
        } catch (QuestionNotFoundException e) {
            e.printStackTrace();
        }
    }

}
