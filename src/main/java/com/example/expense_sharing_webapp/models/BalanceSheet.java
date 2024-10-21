package com.example.expense_sharing_webapp.models;

import java.util.List;
import java.util.Map;

public class BalanceSheet {
    private User user;
    private List<Expense> expenses;
    private Map<Long, Double> userBalance; // Mapping of expense ID to amount the user owes

    public BalanceSheet(User user, List<Expense> expenses, Map<Long, Double> userBalance) {
        this.user = user;
        this.expenses = expenses;
        this.userBalance = userBalance;
    }

    public User getUser() {
        return user;
    }

    public List<Expense> getExpenses() {
        return expenses;
    }

    public Map<Long, Double> getUserBalance() {
        return userBalance;
    }

    public double getTotalBalance() {
        return userBalance.values().stream().mapToDouble(Double::doubleValue).sum();
    }
}

