package com.bsd.spring.service;

import com.bsd.spring.repository.UserRepository;
import com.bsd.spring.entity.User;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
public class UserServiceImpl implements UserService {

    private final Logger log = LoggerFactory.getLogger(UserServiceImpl.class);

    private final UserRepository repo;

    public UserServiceImpl(UserRepository repo) {
        this.repo = repo;
    }

    @Override
    public Optional<User> getUserByEmail(String email) {
        return Optional.ofNullable(repo.findUserByEmail(email));
    }

    @Override
    public User saveUser(User user) {
        return repo.save(user);
    }

    @Override
    public List<User> getUsersByEmailLike(String email) {
        return repo.findByEmailIgnoreCaseLike(email);
    }

    @Override
    public List<User> getUsers() {
        return repo.findAll();
    }

    @Override
    public List<User> getAllUsers() {
        return repo.findAll();
    }

}
