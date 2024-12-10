package org.example.controller;

import org.example.model.Expense;
import org.example.service.ExpenseService;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;


@RestController
public class ExpenseController {

    private final ExpenseService expenseService;

    public ExpenseController(ExpenseService expenseService) {
        this.expenseService = expenseService;
    }

    @GetMapping("/expenses/day/{date}")
    public ResponseEntity<List<Expense>> getExpenseByDay(@PathVariable String date){
        return ResponseEntity.ok(expenseService.getExpenseByDay(date));
    }

    @GetMapping("/expense/category/{category}/month")
    public ResponseEntity<List<Expense>> getExpensesByCategoryAndMonth(
            @PathVariable String category,
            @RequestParam String month){
        return ResponseEntity.ok(expenseService.getExpenseByCategoryAndMonth(category,month));
    }

    @GetMapping("/expenses/categories")
    public ResponseEntity<List<String>> getAllExpenseCategories(){
        List<String> categories = expenseService.getAllExpenseCategories();

        if(categories.isEmpty()){
            return ResponseEntity.status(HttpStatus.NO_CONTENT).body(null);
        }
        return ResponseEntity.ok(categories);
    }

    @GetMapping("/expenses/{id}")
    public ResponseEntity<Optional<Expense>> getExpenseById(@PathVariable Long id){
        return ResponseEntity.ok(expenseService.getExpenseById(id));
    }

    @PostMapping("/expenses")
    public ResponseEntity<Expense> addExpense(@RequestBody Expense expense){
        Expense newExpense = expenseService.addExpense(expense);
        return new ResponseEntity<>(newExpense, HttpStatus.CREATED);
    }

    @PutMapping("/expenses/{id}")
    public ResponseEntity<Expense> updateExpense(@PathVariable Long id, @RequestBody Expense expense){
        expense.setId(id);
        boolean isUpdated = expenseService.updateExpense(expense);
        if(isUpdated){
            return new ResponseEntity<>(expense, HttpStatus.OK);
        }else{
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @DeleteMapping("/expenses/{id}")
    public ResponseEntity<Void> deleteExpense(@PathVariable Long id){
        boolean isDeleted = expenseService.deleteExpense(id);
        if(isDeleted){
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }
}
