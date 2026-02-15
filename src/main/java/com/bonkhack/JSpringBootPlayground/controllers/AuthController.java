package com.bonkhack.JSpringBootPlayground.controllers;

import com.bonkhack.JSpringBootPlayground.dto.UserResponse;
import com.bonkhack.JSpringBootPlayground.exceptions.BadRequestException;
import com.bonkhack.JSpringBootPlayground.exceptions.ResourceNotFoundException;
import com.bonkhack.JSpringBootPlayground.models.User;
import com.bonkhack.JSpringBootPlayground.security.JwtUtil;
import com.bonkhack.JSpringBootPlayground.services.UserService;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/auth")

public class AuthController {

    private final UserService userService;
    private final PasswordEncoder passwordEncoder;
    private final JwtUtil jwtUtil;

    public AuthController(UserService userService, PasswordEncoder passwordEncoder, JwtUtil jwtUtil) {
        this.userService = userService;
        this.passwordEncoder = passwordEncoder;
        this.jwtUtil = jwtUtil;
    }

    @PostMapping("/register")
    public UserResponse register(@RequestParam String username,
                                 @RequestParam String email,
                                 @RequestParam String password) {

        String encodedPassword = passwordEncoder.encode(password);
        User user = userService.register(username, email, encodedPassword);
        return userService.toUserResponse(user);
    }

    @PostMapping("/login")
    public String login(@RequestParam String username, @RequestParam String password) {

        User user = userService.findByUsername(username)
                .orElseThrow(() -> new ResourceNotFoundException("User not found"));

        if (!passwordEncoder.matches(password, user.getPassword())) {
            throw new BadRequestException("Invalid credentials");
        }

        return jwtUtil.generateToken(username);
    }


}
