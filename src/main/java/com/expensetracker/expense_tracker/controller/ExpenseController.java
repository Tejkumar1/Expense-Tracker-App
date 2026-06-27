package com.expensetracker.expense_tracker.controller;

import com.expensetracker.expense_tracker.model.Expense;
import com.expensetracker.expense_tracker.model.User;
import com.expensetracker.expense_tracker.service.ExpenseService;
import com.expensetracker.expense_tracker.service.UserService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/expenses")
@CrossOrigin(origins = "http://localhost:3000")
@Tag(name = "Expenses", description = "Add, view, and delete expenses for the logged-in user")
public class ExpenseController {

    private final ExpenseService expenseService;
    private final UserService userService;

    public ExpenseController(ExpenseService expenseService, UserService userService) {
        this.expenseService = expenseService;
        this.userService = userService;
    }

    private User getLoggedInUser(Authentication authentication) {
        String email = authentication.getName();
        return userService.findByEmail(email);
    }

    @Operation(summary = "Add a new expense for the logged-in user")
    @PostMapping
    public ResponseEntity<Expense> addExpense(@Valid @RequestBody Expense expense,
                                               Authentication authentication) {
        User user = getLoggedInUser(authentication);
        Expense saved = expenseService.addExpense(expense, user);
        return ResponseEntity.status(HttpStatus.CREATED).body(saved);
    }

    @Operation(summary = "Get all expenses for the logged-in user (no pagination)")
    @GetMapping
    public ResponseEntity<List<Expense>> getExpenses(Authentication authentication) {
        User user = getLoggedInUser(authentication);
        return ResponseEntity.ok(expenseService.getExpensesForUser(user.getId()));
    }

    // Level 2 fix: paginated endpoint — e.g. GET /api/expenses/page?page=0&size=10
    @Operation(summary = "Get expenses for the logged-in user, paginated")
    @GetMapping("/page")
    public ResponseEntity<Page<Expense>> getExpensesPaginated(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size,
            Authentication authentication) {
        User user = getLoggedInUser(authentication);
        Pageable pageable = PageRequest.of(page, size);
        return ResponseEntity.ok(expenseService.getExpensesForUser(user.getId(), pageable));
    }

    @Operation(summary = "Delete an expense by id (must belong to the logged-in user)")
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteExpense(@PathVariable Long id,
                                               Authentication authentication) {
        User user = getLoggedInUser(authentication);
        expenseService.deleteExpense(id, user.getId());
        return ResponseEntity.noContent().build();
    }
}
