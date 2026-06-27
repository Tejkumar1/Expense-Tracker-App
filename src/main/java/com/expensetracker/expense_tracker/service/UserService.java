package com.expensetracker.expense_tracker.service;

import com.expensetracker.expense_tracker.model.User;
import com.expensetracker.expense_tracker.repository.UserRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class UserService {

    private static final Logger log = LoggerFactory.getLogger(UserService.class);

    private final UserRepository userRepository;
    private final PasswordEncoder encoder;

    public UserService(UserRepository userRepository, PasswordEncoder encoder) {
        this.userRepository = userRepository;
        this.encoder = encoder;
    }

    public User register(User user) {
        log.info("Registration attempt for email={}", user.getEmail());
        if (userRepository.existsByEmail(user.getEmail())) {
            log.warn("Registration failed — email already exists: {}", user.getEmail());
            throw new RuntimeException("Email already in use");
        }
        user.setPassword(encoder.encode(user.getPassword()));
        User saved = userRepository.save(user);
        log.info("User registered successfully with id={}", saved.getId());
        return saved;
    }

    public User login(String email, String password) {
        log.info("Login attempt for email={}", email);
        User user = userRepository.findByEmail(email)
                .orElseThrow(() -> {
                    log.warn("Login failed — user not found: {}", email);
                    return new RuntimeException("User not found");
                });

        if (!encoder.matches(password, user.getPassword())) {
            log.warn("Login failed — invalid password for email={}", email);
            throw new RuntimeException("Invalid credentials");
        }
        log.info("Login successful for email={}", email);
        return user;
    }

    public User findByEmail(String email) {
        return userRepository.findByEmail(email)
                .orElseThrow(() -> new RuntimeException("User not found"));
    }
}
