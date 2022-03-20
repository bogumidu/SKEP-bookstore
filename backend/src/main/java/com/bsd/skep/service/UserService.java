package com.bsd.skep.service;

import com.bsd.skep.entity.User;

import java.util.List;
import java.util.Optional;

public interface UserService {

    Optional<User> getUserByEmail(String email);

    User saveUser(User user);

    List<User> getUsersByEmailLike(String email);

    List<User> getUsers();

    List<User> getAllUsers();
}
