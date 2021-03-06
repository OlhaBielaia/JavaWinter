package com.taxi.service.impl;

import com.taxi.dao.Page;
import com.taxi.dao.UserDao;
import com.taxi.dao.impl.Pageable;
import com.taxi.domain.User;
import com.taxi.service.PasswordEncryptor;
import com.taxi.service.UserService;
import com.taxi.service.validator.Validator;

public class UserServiceImpl implements UserService {
    private static final int USER_PER_PAGE = 5;
    private final UserDao userRepository;
    private final PasswordEncryptor passwordEncryptor;
    private final Validator<User> userValidator;

    public UserServiceImpl(UserDao userRepository, PasswordEncryptor passwordEncryptor,
                           Validator<User> userValidator) {
        this.userRepository = userRepository;
        this.passwordEncryptor = passwordEncryptor;
        this.userValidator = userValidator;
    }

    @Override
    public boolean login(String email, String password) {

        String encryptPassword = passwordEncryptor.encrypt(password);

        return userRepository.findByEmail(email)
                .map(user -> user.getPassword())
                .filter(pass -> pass.equals(encryptPassword))
                .isPresent();
    }

    @Override
    public User register(User user) {
        userValidator.validate(user);

        if (userRepository.findByEmail(user.getEmail()).isPresent()) {
            throw new RuntimeException("message");
        }

        userRepository.save(user);

        //id?
        return user;
    }

    @Override
    public Pageable<User> findAll(int page) {
        if(page<=0){
            page=1;
        }
        //more than MAX
        return userRepository.findAll(new Page(page, USER_PER_PAGE));
    }

    @Override
    public User findById(int id) {
        return userRepository.findById(id).get();
    }

    @Override
    public User findByEmail(String email) {
        return userRepository.findByEmail(email).get();
    }
}
