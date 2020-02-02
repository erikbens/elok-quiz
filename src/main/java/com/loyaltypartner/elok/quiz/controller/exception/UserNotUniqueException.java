package com.loyaltypartner.elok.quiz.controller.exception;

import lombok.Getter;

@Getter
public class UserNotUniqueException extends Exception {

    /**
     * 
     */
    private static final long serialVersionUID = 395380519598891684L;
    
    private String name;
    
    public UserNotUniqueException(String name) {
        super();
        this.name = name;
    }

}
