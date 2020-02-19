package com.loyaltypartner.elok.quiz.service;

import java.util.List;
import java.util.Optional;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.loyaltypartner.elok.quiz.controller.exception.LoginFailedException;
import com.loyaltypartner.elok.quiz.controller.exception.UserNotFoundException;
import com.loyaltypartner.elok.quiz.controller.exception.UserNotUniqueException;
import com.loyaltypartner.elok.quiz.helpers.AES;
import com.loyaltypartner.elok.quiz.model.User;
import com.loyaltypartner.elok.quiz.model.dto.UserLoginResponseDTO;
import com.loyaltypartner.elok.quiz.repository.UserRepository;

@Service
public class UserService implements IUserService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private ModelMapper modelMapper;

    private static final String SECRET = "eLOK-Quiz-2019";

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
    public User create(User entity) throws UserNotUniqueException {
        User user = userRepository.findByName(entity.getName());
        if (user == null) {
            entity.setPass(AES.encrypt(entity.getPass(), SECRET));
            return userRepository.save(entity);
        }
        throw new UserNotUniqueException(entity.getName());
    }

    @Override
    public User update(Long userId, User entity) throws UserNotFoundException {
        User user = findById(userId);

        user.setName(entity.getName());
        user.setPass(AES.encrypt(entity.getPass(), SECRET));

        return userRepository.save(user);
    }

    @Override
    public UserLoginResponseDTO login(String name, String pass) throws LoginFailedException {
        User user = this.userRepository.findByName(name);
        if (user != null && AES.decrypt(user.getPass(), SECRET).equals(pass)) {
            UserLoginResponseDTO dto = modelMapper.map(user, UserLoginResponseDTO.class);
            dto.setToken("999999");
            return dto;
        }
        throw new LoginFailedException(name, pass);
    }

}
