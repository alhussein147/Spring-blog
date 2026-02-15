package com.bonkhack.JSpringBootPlayground.controllers;

import com.bonkhack.JSpringBootPlayground.dto.UserResponse;
import com.bonkhack.JSpringBootPlayground.exceptions.ResourceNotFoundException;
import com.bonkhack.JSpringBootPlayground.models.User;
import com.bonkhack.JSpringBootPlayground.services.PostsService;
import com.bonkhack.JSpringBootPlayground.services.UserService;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/users")
public class UserController {
    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }



    // Get user by username
    @GetMapping("/{username}")
    public UserResponse getByUsername(@PathVariable String username) {
        // TODO: 15-Feb-26 show user posts in this endpoint
        User user = userService.findByUsername(username)
                .orElseThrow(() -> new ResourceNotFoundException("User not found"));
        return userService.toUserResponse(user);
    }

}
