package com.loyaltypartner.elok.quiz.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.loyaltypartner.elok.quiz.controller.exception.UserNotFoundException;
import com.loyaltypartner.elok.quiz.model.User;
import com.loyaltypartner.elok.quiz.repository.UserRepository;

@Service
public class UserService implements IUserService {

    @Autowired
    private UserRepository userRepository;

    @Override
    public List<User> findAll() {
        return userRepository.findAll();
    }

    @Override
    public User findById(Long id) throws UserNotFoundException {
        Optional<User> optional = userRepository.findById(id);
        if (!optional.isPresent()) {
            throw new UserNotFoundException();
        }
            
        return optional.get();
    }

    @Override
    public void delete(User entity) {
        userRepository.delete(entity);
    }

    @Override
    public User create(User entity) {
        return userRepository.save(entity);
    }
    
    @Override
    public User update(Long userId, User entity) throws UserNotFoundException {
        User user = findById(userId);
        
        user.setName(entity.getName());
        user.setPass(entity.getPass());
        
        return userRepository.save(user);
    }

}
