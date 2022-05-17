package com.bsd.skep.controller;


import com.bsd.skep.entity.User;
import com.bsd.skep.model.AuthDTO;
import com.bsd.skep.model.LoginCredentials;
import com.bsd.skep.service.UserService;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController()
@RequestMapping("/api/auth")
public class AuthController {

    private final UserService userService;

    public AuthController(UserService userService) {
        this.userService = userService;
    }

    @PostMapping("/register")
    public AuthDTO register(@RequestBody LoginCredentials credentials) {
        User user = userService.register(credentials.getUsername(), credentials.getPassword());
        if (user == null) {
            return new AuthDTO(false, "Username already exists");
        }
        return new AuthDTO(user.getUsername());
    }
}
