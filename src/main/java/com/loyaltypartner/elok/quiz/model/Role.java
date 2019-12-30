package com.loyaltypartner.elok.quiz.model;

public enum Role {

    ADMIN("Admin"), USER("User");

    private String name;

    private Role(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

}
