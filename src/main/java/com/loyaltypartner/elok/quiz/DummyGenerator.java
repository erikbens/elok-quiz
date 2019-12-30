package com.loyaltypartner.elok.quiz;

import java.util.Random;

import com.loyaltypartner.elok.quiz.model.Answer;
import com.loyaltypartner.elok.quiz.model.Difficulty;
import com.loyaltypartner.elok.quiz.model.Domain;
import com.loyaltypartner.elok.quiz.model.Question;
import com.loyaltypartner.elok.quiz.model.Role;
import com.loyaltypartner.elok.quiz.model.User;

public class DummyGenerator {
    
    public static Question generateQuestion(Domain domain) {
        return new Question("Some title " + new Random().nextInt(), "Some text", "Some image", "me", Difficulty.EASY, domain);
    }
    
    public static Answer generateAnswer(Question question) {
        return new Answer("Some Answer " + new Random().nextInt(), new Random().nextInt(2) == 1 ? true : false, question);
    }
    
    public static User generateUser() {
        return new User("Some name " + new Random().nextInt(), "1234", Role.ADMIN);
    }
    
    public static Domain generateDomain() {
        return new Domain("Some name " + new Random().nextInt());
    }

}
