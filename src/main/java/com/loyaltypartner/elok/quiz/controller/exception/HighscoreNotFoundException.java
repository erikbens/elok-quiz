package com.loyaltypartner.elok.quiz.controller.exception;

import com.loyaltypartner.elok.quiz.model.Difficulty;

import lombok.Getter;

@Getter
public class HighscoreNotFoundException extends Exception {

    /**
     * 
     */
    private static final long serialVersionUID = 8976107835575826018L;
    
    private Difficulty difficulty;
    private Long domainId;
    
    public HighscoreNotFoundException(Difficulty difficulty, Long domainId) {
        this.difficulty = difficulty;
        this.domainId = domainId;
    }

}
