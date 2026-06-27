package com.expensetracker.expense_tracker.service;

import com.expensetracker.expense_tracker.model.Expense;
import com.expensetracker.expense_tracker.model.User;
import com.expensetracker.expense_tracker.repository.ExpenseRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDate;
import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class ExpenseServiceTest {

    @Mock
    private ExpenseRepository expenseRepository;

    @InjectMocks
    private ExpenseService expenseService;

    @Test
    void shouldAddExpenseAndAssignLoggedInUser() {
        User user = new User("Tej", "tej@gmail.com", "hashed");
        user.setId(1L);

        Expense expense = new Expense();
        expense.setTitle("Lunch");
        expense.setAmount(150);
        expense.setCategory("Food");
        expense.setDate(LocalDate.now());

        when(expenseRepository.save(any(Expense.class))).thenAnswer(invocation -> invocation.getArgument(0));

        Expense result = expenseService.addExpense(expense, user);

        assertNotNull(result);
        assertEquals(user, result.getUser());
        verify(expenseRepository, times(1)).save(expense);
    }

    @Test
    void shouldReturnOnlyExpensesBelongingToTheUser() {
        Expense e1 = new Expense();
        e1.setTitle("Lunch");
        Expense e2 = new Expense();
        e2.setTitle("Auto");

        when(expenseRepository.findByUserId(1L)).thenReturn(Arrays.asList(e1, e2));

        List<Expense> result = expenseService.getExpensesForUser(1L);

        assertEquals(2, result.size());
        verify(expenseRepository, times(1)).findByUserId(1L);
    }

    @Test
    void shouldDeleteExpenseWhenItBelongsToUser() {
        when(expenseRepository.existsByIdAndUserId(5L, 1L)).thenReturn(true);

        expenseService.deleteExpense(5L, 1L);

        verify(expenseRepository, times(1)).deleteById(5L);
    }

    @Test
    void shouldThrowExceptionWhenDeletingExpenseNotOwnedByUser() {
        when(expenseRepository.existsByIdAndUserId(5L, 1L)).thenReturn(false);

        RuntimeException exception = assertThrows(RuntimeException.class,
                () -> expenseService.deleteExpense(5L, 1L));

        assertEquals("Expense not found or you don't have permission to delete it", exception.getMessage());
        verify(expenseRepository, never()).deleteById(any());
    }
}
