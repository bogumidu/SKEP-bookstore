package com.bsd.skep.service;

import com.bsd.skep.entity.User;
import com.bsd.skep.model.LoginCredentials;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.provisioning.UserDetailsManager;

import java.util.List;
import java.util.Optional;

public interface UserService extends UserDetailsService {

    Optional<User> getUserByEmail(String email);

    User saveUser(User user);

    List<User> getUsersByEmailLike(String email);

    List<User> getUsers();

    List<User> getAllUsers();

    User register(String email, String password);
}
