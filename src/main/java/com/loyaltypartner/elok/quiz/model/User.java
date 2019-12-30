package com.loyaltypartner.elok.quiz.model;

import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Getter
@Setter
@NoArgsConstructor
public class User extends BaseEntity {
    
    private String name;
    private String pass;
    
    @Enumerated(EnumType.STRING)
    private Role role;

    public User(String name, String pass, Role role) {
        super();
        this.name = name;
        this.pass = pass;
        this.role = role;
    }
    
}
