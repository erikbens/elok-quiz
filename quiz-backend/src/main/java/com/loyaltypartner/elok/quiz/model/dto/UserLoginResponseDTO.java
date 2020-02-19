package com.loyaltypartner.elok.quiz.model.dto;

import com.loyaltypartner.elok.quiz.model.Role;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@Getter
@Setter
public class UserLoginResponseDTO {
    
    private Long id;
    private String name;
    private String password;
    private String token;
    private Role role;

}
