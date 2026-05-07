package com.expensetracker.expense_tracker.controller;

import com.expensetracker.expense_tracker.model.User;
import com.expensetracker.expense_tracker.security.JwtUtil;
import com.expensetracker.expense_tracker.service.UserService;
import org.springframework.web.bind.annotation.*;

// Bug 1 fix: JwtUtil import was missing — added above.
// Bug 6 fix: JwtUtil is now injected as a Spring Bean instead of called statically.
@RestController
@RequestMapping("/api/auth")
@CrossOrigin(origins = "http://localhost:3000")
public class AuthController {

    private final UserService userService;
    private final JwtUtil jwtUtil;

    public AuthController(UserService userService, JwtUtil jwtUtil) {
        this.userService = userService;
        this.jwtUtil = jwtUtil;
    }

    @PostMapping("/register")
    public User register(@RequestBody User user) {
        return userService.register(user);
    }

    @PostMapping("/login")
    public String login(@RequestBody User user) {
        User loggedIn = userService.login(user.getEmail(), user.getPassword());
        return jwtUtil.generateToken(loggedIn.getEmail());
    }
}