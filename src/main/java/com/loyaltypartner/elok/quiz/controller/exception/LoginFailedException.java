package com.loyaltypartner.elok.quiz.controller.exception;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class LoginFailedException extends Exception {

    /**
     * 
     */
    private static final long serialVersionUID = -7739045019538334077L;
    
    private String name;
    private String pass;
    
    public LoginFailedException() {
        super();
    }
    
    public LoginFailedException(String name, String pass) {
        this();
        this.name = name;
        this.pass = pass;
    }

}
