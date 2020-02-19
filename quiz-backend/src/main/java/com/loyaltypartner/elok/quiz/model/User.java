package com.loyaltypartner.elok.quiz.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Getter
@Setter
@NoArgsConstructor
@NamedQueries({
    @NamedQuery(name = "User.findByName", query = "SELECT u FROM User u WHERE u.name = :name")
})
public class User extends BaseEntity {
    
    private String name;
    
    @Column(length = 60)
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
