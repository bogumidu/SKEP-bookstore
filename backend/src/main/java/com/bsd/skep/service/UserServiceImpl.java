package com.bsd.skep.service;

import com.bsd.skep.entity.User;
import com.bsd.skep.repository.UserRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class UserServiceImpl implements UserService {

    private final Logger log = LoggerFactory.getLogger(UserServiceImpl.class);

    private final UserRepository repo;
    private final PasswordEncoder passwordEncoder;

    public UserServiceImpl(UserRepository repo, PasswordEncoder passwordEncoder) {
        this.repo = repo;
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    public Optional<User> getUserByEmail(String email) {
        return repo.findUserByEmail(email);
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

    @Override
    public User register(String email, String password) {
        UserDetails user = loadUserByUsername(email);
        if (user != null) {
            return null;
        }
        return saveUser(User.builder()
                .email(email)
                .password(passwordEncoder.encode(password))
                .role("USER")
                .build());
    }

    @Override
    public UserDetails loadUserByUsername(String s) throws UsernameNotFoundException {
        return repo.findUserByEmail(s).orElse(null);
    }

//    @Override
//    public void createUser(UserDetails user) {
//        User build = User.builder().name(user.getUsername()).email(user.getUsername()).password(user.getPassword())
//                .role(user.getAuthorities().stream().findFirst().map(GrantedAuthority::getAuthority)
//                        .orElse("USER")).build();
//        repo.save(build);
//    }
//
//    @Override
//    public void updateUser(UserDetails user) {}
//
//    @Override
//    public void deleteUser(String username) {
//        repo.findUserByEmail(username).ifPresent(repo::delete);
//    }
//
//    @Override
//    public void changePassword(String oldPassword, String newPassword) {
//        Authentication currentUser = SecurityContextHolder.getContext()
//                .getAuthentication();
//
//        if (currentUser == null) {
//            throw new AccessDeniedException(
//                    "Can't change password as no Authentication object found in context "
//                            + "for current user.");
//        }
//
//        String username = currentUser.getName();
//        repo.findUserByEmail(username).ifPresent(u -> {
//            u.setPassword(newPassword);
//            repo.save(u);
//        });
//    }
//
//    @Override
//    public boolean userExists(String username) {
//        return repo.findUserByEmail(username).isPresent();
//    }
}
