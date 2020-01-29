package com.loyaltypartner.elok.quiz.imports.converter;

import com.loyaltypartner.elok.quiz.model.Answer;
import com.loyaltypartner.elok.quiz.model.Difficulty;
import com.loyaltypartner.elok.quiz.model.Question;

public class XmlToModelConverter {
    
    public static Question toModelQuestion(com.loyaltypartner.elok.quiz.xml.Question input) {
        Question question = new Question();
        question.setDifficulty(toModelDifficulty(input.getLevel()));
        question.setCreatedBy("Import");
        question.setImage(input.getImg());
        question.setText(input.getText());
        question.setTitle(input.getText().substring(0, input.getText().length() > 30 ? 30 : input.getText().length()));
        return question;
    }
    
    public static Answer toModelAnswer(com.loyaltypartner.elok.quiz.xml.Answer input) {
        Answer answer = new Answer();
        answer.setCorrect(input.isSolution());
        answer.setText(input.getText());
        return answer;
    }
    
    public static Difficulty toModelDifficulty(com.loyaltypartner.elok.quiz.xml.Difficulty input) {
        if (input == null) {
            return Difficulty.EASY;
        }
        switch (input) {
        case A:
            return Difficulty.EASY;
        case B:
            return Difficulty.MEDIUM;
        case C:
            return Difficulty.HARD;
        default:
            return Difficulty.EASY;
        }
    }

}
