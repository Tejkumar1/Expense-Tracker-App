package com.expensetracker.expense_tracker.service;

import com.expensetracker.expense_tracker.model.User;
import com.expensetracker.expense_tracker.repository.UserRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class UserServiceTest {

    @Mock
    private UserRepository userRepository;

    @Mock
    private PasswordEncoder encoder;

    @InjectMocks
    private UserService userService;

    @Test
    void shouldRegisterUserSuccessfullyWhenEmailIsNew() {
        User user = new User("Tej", "tej@gmail.com", "1234");

        when(userRepository.existsByEmail("tej@gmail.com")).thenReturn(false);
        when(encoder.encode("1234")).thenReturn("hashedPassword");
        when(userRepository.save(any(User.class))).thenAnswer(invocation -> invocation.getArgument(0));

        User result = userService.register(user);

        assertNotNull(result);
        assertEquals("hashedPassword", result.getPassword());
        verify(userRepository, times(1)).save(user);
    }

    @Test
    void shouldThrowExceptionWhenEmailAlreadyExists() {
        User user = new User("Tej", "tej@gmail.com", "1234");

        when(userRepository.existsByEmail("tej@gmail.com")).thenReturn(true);

        RuntimeException exception = assertThrows(RuntimeException.class,
                () -> userService.register(user));

        assertEquals("Email already in use", exception.getMessage());
        verify(userRepository, never()).save(any(User.class));
    }

    @Test
    void shouldLoginSuccessfullyWithCorrectCredentials() {
        User user = new User("Tej", "tej@gmail.com", "hashedPassword");

        when(userRepository.findByEmail("tej@gmail.com")).thenReturn(Optional.of(user));
        when(encoder.matches("1234", "hashedPassword")).thenReturn(true);

        User result = userService.login("tej@gmail.com", "1234");

        assertNotNull(result);
        assertEquals("tej@gmail.com", result.getEmail());
    }

    @Test
    void shouldThrowExceptionWhenUserNotFoundOnLogin() {
        when(userRepository.findByEmail("notfound@gmail.com")).thenReturn(Optional.empty());

        RuntimeException exception = assertThrows(RuntimeException.class,
                () -> userService.login("notfound@gmail.com", "1234"));

        assertEquals("User not found", exception.getMessage());
    }

    @Test
    void shouldThrowExceptionWhenPasswordIsIncorrect() {
        User user = new User("Tej", "tej@gmail.com", "hashedPassword");

        when(userRepository.findByEmail("tej@gmail.com")).thenReturn(Optional.of(user));
        when(encoder.matches(anyString(), eq("hashedPassword"))).thenReturn(false);

        RuntimeException exception = assertThrows(RuntimeException.class,
                () -> userService.login("tej@gmail.com", "wrongpassword"));

        assertEquals("Invalid credentials", exception.getMessage());
    }
}
