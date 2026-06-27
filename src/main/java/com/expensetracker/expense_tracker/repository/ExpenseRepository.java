package com.expensetracker.expense_tracker.repository;

import com.expensetracker.expense_tracker.model.Expense;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ExpenseRepository extends JpaRepository<Expense, Long> {

    // Used by the old non-paginated getAll (kept for backward compatibility / tests)
    List<Expense> findByUserId(Long userId);

    // Level 2: paginated fetch — only loads a page at a time instead of everything
    Page<Expense> findByUserId(Long userId, Pageable pageable);

    // Used to verify ownership before delete (security check)
    boolean existsByIdAndUserId(Long id, Long userId);
}
