package com.loyaltypartner.elok.quiz.service;

import com.loyaltypartner.elok.quiz.controller.exception.LoginFailedException;
import com.loyaltypartner.elok.quiz.model.User;
import com.loyaltypartner.elok.quiz.model.dto.UserLoginResponseDTO;

public interface IUserService extends BaseService<User, Long> {

    UserLoginResponseDTO login(String name, String pass) throws LoginFailedException;

}
