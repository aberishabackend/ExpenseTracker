package org.university.service;

import org.university.model.Expense;
import org.university.utils.ExpenseDataLoader;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.concurrent.atomic.AtomicLong;

@Service
@Profile("json")
public class ExpenseServiceImpl implements ExpenseService{

    private static final AtomicLong idCounter = new AtomicLong();


    @Override
    public List<Expense> getExpenseByDay(String date) {
        return ExpenseDataLoader.getExpenses().stream().filter(
                expense -> expense.getDate().equalsIgnoreCase(date)).toList();
    }

    @Override
    public List<Expense> getExpenseByCategoryAndMonth(String category, String month) {
        return ExpenseDataLoader.getExpenses().stream().filter(
                expense -> expense.getCategory().equalsIgnoreCase(category)
                        &&expense.getDate().startsWith(month)).toList();

    }

    @Override
    public List<String> getAllExpenseCategories() {
        return ExpenseDataLoader.getExpenses().stream().map(Expense::getCategory).distinct().toList();
    }

    @Override
    public Optional<Expense> getExpenseById(Long id) {
        return ExpenseDataLoader.getExpenses().stream().filter(
                expense -> expense.getId().equals(id)).findFirst();
    }

    @Override
    public Expense addExpense(Expense expense) {
        expense.setId(idCounter.incrementAndGet());
        ExpenseDataLoader.getExpenses().add(expense);
        return expense;
    }

    @Override
    public boolean updateExpense(Expense updatedExpense) {
        Optional<Expense> existingExpense = getExpenseById(updatedExpense.getId());
        if(existingExpense.isPresent()){
            ExpenseDataLoader.getExpenses().remove(existingExpense.get());
            ExpenseDataLoader.getExpenses().add(updatedExpense);
            return true;
        }
        return false;
    }

    @Override
    public boolean deleteExpense(Long id) {
        Optional<Expense> existingExpense = getExpenseById(id);
        if(existingExpense.isPresent()){
            ExpenseDataLoader.getExpenses().remove(existingExpense.get());
            return true;
        }
        return false;
    }
}
