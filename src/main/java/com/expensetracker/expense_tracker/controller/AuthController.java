package com.expensetracker.expense_tracker.controller;

import com.expensetracker.expense_tracker.model.User;
import com.expensetracker.expense_tracker.service.UserService;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/auth")
@CrossOrigin(origins = "http://localhost:3000")
public class AuthController {

    private final UserService userService;

    public AuthController(UserService userService) {
        this.userService = userService;
    }

    @PostMapping("/register")
    public User register(@RequestBody User user) {
        return userService.register(user);
    }

    @PostMapping("/login")
public String login(@RequestBody User user) {
    User loggedIn = userService.login(user.getEmail(), user.getPassword());
    return JwtUtil.generateToken(loggedIn.getEmail());
}

}
