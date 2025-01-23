package org.university.service;

import org.university.model.AppUser;
import org.university.model.Expense;
import org.university.repository.ExpenseRepository;
import org.university.utils.ExpenseDataLoader;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.concurrent.atomic.AtomicLong;
import java.util.stream.Collectors;

@Service
public class ExpenseServiceImpl implements ExpenseService{

    public ExpenseServiceImpl(ExpenseRepository expenseRepository, UserService userService) {
        this.expenseRepository = expenseRepository;
        this.userService = userService;
    }

    private final ExpenseRepository expenseRepository;
    private final UserService userService;

    //private static final AtomicLong idCounter = new AtomicLong();


    @Override
    public List<Expense> getAllUserExpenses(Long userId) {
        return new ArrayList<>(
                expenseRepository.findByUserIdOrderByDateDesc(userId));
    }

    @Override
    public List<Expense> getExpenseByDay(String date, Long userId) {
        return expenseRepository.findByUserIdOrderByDateDesc(userId)
                .stream().filter(expense -> expense.getDate()
                        .equals(date)).toList();
    }

    @Override
    public List<Expense> getExpenseByCategoryAndMonth(String category, String month, Long userId) {
        return expenseRepository.findByUserIdOrderByDateDesc(userId)
                .stream().filter(
                expense -> expense.getCategory()
                        .equalsIgnoreCase(category)
                        &&expense.getDate().startsWith(month)).toList();

    }

    @Override
    public List<String> getAllExpenseCategories(Long userId) {
        return expenseRepository.findByUserIdOrderByDateDesc(userId).stream().map(Expense::getCategory).distinct().toList();
    }

    @Override
    public Optional<Expense> getExpenseById(Long id, Long userId) {
        return expenseRepository.findByIdAndUserId(id,userId).stream().filter(
                expense -> expense.getId().equals(id)).findFirst();
    }

    @Override
    public Expense addExpense(Expense expense,Long userId) {
        Optional<AppUser> userOptional =
                userService.findUserById(userId);
        if(userOptional.isPresent()) {
            AppUser user = userOptional.get();
            expense.setUser(user);
            return expenseRepository.save(expense);
        }else {
            throw new RuntimeException("User not found");
        }
    }

    @Override
    public boolean updateExpense(Expense updatedExpense, Long userId) {
        Optional<Expense> existingExpense =
                expenseRepository.findByIdAndUserId(
                        updatedExpense.getId(),userId);
        if(existingExpense.isPresent()){
            updatedExpense.setUser(existingExpense.get().getUser());
            expenseRepository.save(updatedExpense);
            return true;
        }
        return false;
    }

    @Override
    public boolean deleteExpense(Long id, Long userId) {
        Optional<Expense> existingExpense = expenseRepository.findByIdAndUserId(id,userId);
        if(existingExpense.isPresent()){
            expenseRepository.deleteById(id);
            return true;
        }
        return false;
    }

}
