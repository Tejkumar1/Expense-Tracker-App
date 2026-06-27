package com.expensetracker.expense_tracker.service;

import com.expensetracker.expense_tracker.model.Expense;
import com.expensetracker.expense_tracker.model.User;
import com.expensetracker.expense_tracker.repository.ExpenseRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ExpenseService {

    // Level 2 fix: logging so we can trace what happens in production
    private static final Logger log = LoggerFactory.getLogger(ExpenseService.class);

    private final ExpenseRepository expenseRepository;

    public ExpenseService(ExpenseRepository expenseRepository) {
        this.expenseRepository = expenseRepository;
    }

    public Expense addExpense(Expense expense, User user) {
        log.info("Adding expense '{}' for user id={}", expense.getTitle(), user.getId());
        expense.setUser(user);
        Expense saved = expenseRepository.save(expense);
        log.info("Expense saved with id={}", saved.getId());
        return saved;
    }

    // Kept for places that still need the full unpaginated list
    public List<Expense> getExpensesForUser(Long userId) {
        log.info("Fetching all expenses for user id={}", userId);
        return expenseRepository.findByUserId(userId);
    }

    // Level 2 fix: paginated fetch — only loads "size" records at a time
    public Page<Expense> getExpensesForUser(Long userId, Pageable pageable) {
        log.info("Fetching expenses for user id={} page={} size={}",
                userId, pageable.getPageNumber(), pageable.getPageSize());
        return expenseRepository.findByUserId(userId, pageable);
    }

    public void deleteExpense(Long id, Long userId) {
        log.info("Attempting to delete expense id={} for user id={}", id, userId);
        if (!expenseRepository.existsByIdAndUserId(id, userId)) {
            log.warn("Delete denied — expense id={} does not belong to user id={}", id, userId);
            throw new RuntimeException("Expense not found or you don't have permission to delete it");
        }
        expenseRepository.deleteById(id);
        log.info("Expense id={} deleted successfully", id);
    }
}
