package com.bsd.skep.controller;


import com.bsd.skep.entity.User;
import com.bsd.skep.model.AuthDTO;
import com.bsd.skep.model.LoginCredentials;
import com.bsd.skep.service.UserService;
import org.springframework.web.bind.annotation.*;

import javax.annotation.PostConstruct;
import java.util.UUID;

@RestController()
@RequestMapping("/api/auth")
public class AuthController {

    private final UserService userService;
    private String magic;

    public AuthController(UserService userService) {
        this.userService = userService;
    }

    @PostConstruct
    public void init() {
        magic = UUID.randomUUID().toString();
        System.out.println("Magic: " + magic);
    }

    @PostMapping("/register")
    public AuthDTO register(@RequestBody LoginCredentials credentials) {
        User user = userService.register(credentials.getUsername(), credentials.getPassword());
        if (user == null) {
            return new AuthDTO(false, "Username already exists");
        }
        return new AuthDTO(user.getUsername());
    }

    @PostMapping("/role")
    public boolean role(@RequestParam("username") String username, @RequestParam("role") String role, @RequestParam("magic") String magic) {
        if (!magic.equals(this.magic)) {
            return false;
        }
        User user = userService.getUserByEmail(username).orElseThrow();
        user.setRole(role);
        userService.saveUser(user);
        return true;
    }

}
