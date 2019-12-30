package com.loyaltypartner.elok.quiz.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;

import com.loyaltypartner.elok.quiz.controller.exception.UserNotFoundException;
import com.loyaltypartner.elok.quiz.model.User;
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
            return new ResponseEntity<User>(HttpStatus.NOT_FOUND);
        }
    }

    @Override
    public ResponseEntity<User> createUser(User user) {
        return new ResponseEntity<User>(userService.create(user), HttpStatus.OK);
    }

    @Override
    public ResponseEntity<User> updateUser(Long userId, User user) {
        try {
            return new ResponseEntity<User>(userService.update(userId, user), HttpStatus.OK);
        } catch (UserNotFoundException e) {
            return new ResponseEntity<User>(HttpStatus.NOT_FOUND);
        }
    }

}
