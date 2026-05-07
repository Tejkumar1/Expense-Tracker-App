package com.expensetracker.expense_tracker.controller;

import com.expensetracker.expense_tracker.model.User;
import com.expensetracker.expense_tracker.service.UserService;
import org.springframework.web.bind.annotation.*;

// Bug 4 fix: was calling userService.registerUser() which didn't exist in UserService.
// Now correctly calls userService.register() which is the actual method name.
@RestController
@RequestMapping("/api/users")
@CrossOrigin(origins = "http://localhost:3000")
public class UserController {

    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    @PostMapping("/register")
    public User register(@RequestBody User user) {
        return userService.register(user);
    }
}