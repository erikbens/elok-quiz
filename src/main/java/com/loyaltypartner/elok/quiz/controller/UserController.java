package com.loyaltypartner.elok.quiz.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;

import com.loyaltypartner.elok.quiz.controller.exception.LoginFailedException;
import com.loyaltypartner.elok.quiz.controller.exception.UserNotFoundException;
import com.loyaltypartner.elok.quiz.controller.exception.UserNotUniqueException;
import com.loyaltypartner.elok.quiz.i18n.Translator;
import com.loyaltypartner.elok.quiz.model.User;
import com.loyaltypartner.elok.quiz.model.UserLoginDTO;
import com.loyaltypartner.elok.quiz.model.UserLoginResponseDTO;
import com.loyaltypartner.elok.quiz.service.IUserService;

@RestController
public class UserController implements IUserController {

    @Autowired
    private IUserService userService;

    @Override
    public ResponseEntity<List<User>> findAll() {
        return new ResponseEntity<List<User>>(userService.findAll(), HttpStatus.OK);
    }

    @Override
    public ResponseEntity<User> findById(Long userId) {
        try {
            return new ResponseEntity<User>(userService.findById(userId), HttpStatus.OK);
        } catch (UserNotFoundException e) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, Translator.toLocale("error.user.notfound"), e);
        }
    }

    @Override
    public ResponseEntity<User> createUser(User user) {
        try {
            return new ResponseEntity<User>(userService.create(user), HttpStatus.OK);
        } catch (UserNotUniqueException e) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, Translator.toLocale("error.user.notunique", new Object[] { e.getName() }), e);
        }
    }

    @Override
    public ResponseEntity<User> updateUser(Long userId, User user) {
        try {
            return new ResponseEntity<User>(userService.update(userId, user), HttpStatus.OK);
        } catch (UserNotFoundException e) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, Translator.toLocale("error.user.notfound"), e);
        }
    }

    @Override
    public ResponseEntity<UserLoginResponseDTO> loginUser(UserLoginDTO loginData) {
        if (loginData != null && loginData.getName() != null && loginData.getPass() != null) {
            try {
                return new ResponseEntity<UserLoginResponseDTO>(userService.login(loginData.getName(), loginData.getPass()), HttpStatus.OK);
            } catch (LoginFailedException e) {
                throw new ResponseStatusException(HttpStatus.BAD_REQUEST, Translator.toLocale("error.login.failed"), e);
            }
        }
        throw new ResponseStatusException(HttpStatus.BAD_REQUEST, Translator.toLocale("error.login.failed"));
    }

}
