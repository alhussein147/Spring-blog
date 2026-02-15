package com.bonkhack.JSpringBootPlayground.services;

import com.bonkhack.JSpringBootPlayground.dto.UserResponse;
import com.bonkhack.JSpringBootPlayground.exceptions.BadRequestException;
import com.bonkhack.JSpringBootPlayground.exceptions.ResourceAlreadyExistException;
import com.bonkhack.JSpringBootPlayground.models.User;
import com.bonkhack.JSpringBootPlayground.repository.UserRepository;
import com.bonkhack.JSpringBootPlayground.util.EmailValidator;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class UserService {
    private final UserRepository userRepository;

    UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Transactional
    public User register(String username, String email, String password) {

        if (username.length() < 5) {
            throw new BadRequestException("username cannot be less than 5 chars");
        }

        if (!EmailValidator.isValid(email)) {
            throw new BadRequestException("invalid email");
        }
        if (password.length() < 8) {
            throw new BadRequestException("weak password");
        }


        if (userRepository.findByUsername(username).isPresent()) {
            throw new ResourceAlreadyExistException("Username taken");
        }
        if (userRepository.findByEmail(email).isPresent()) {
            throw new ResourceAlreadyExistException("Email taken");
        }

        User user = new User();
        user.setEmail(email);
        user.setUsername(username);
        user.setPassword(password);

        return userRepository.save(user);
    }

    public Optional<User> findByUsername(String username) {
        return userRepository.findByUsername(username);
    }

    public Optional<User> findByEmail(String email) {
        return userRepository.findByEmail(email);
    }

    public UserResponse toUserResponse(User user) {
        return new UserResponse(user.getId(), user.getUsername(), user.getEmail(), user.getCreatedAt().toString());
    }
}
