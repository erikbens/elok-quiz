package com.loyaltypartner.elok.quiz.model;

public enum Difficulty {
    
    EASY("Easy"), MEDIUM("Medium"), HARD("Hard");
    
    private String name;

    private Difficulty(String name) {
        this.name = name;
    }
    
    public String getDifficulty() {
        return name;
    }
    
}
