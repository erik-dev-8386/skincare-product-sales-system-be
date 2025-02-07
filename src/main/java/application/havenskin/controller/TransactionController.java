package application.havenskin.controller;

import application.havenskin.BusinessObject.Models.Transactions;
import application.havenskin.DTORequest.TransactionDTO;
import application.havenskin.service.OrderService;
import application.havenskin.service.TransactionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/haven-skin/transaction")
public class TransactionController {
    @Autowired
    private TransactionService transactionService;
    @GetMapping
    public List<Transactions> getAllTransactions() {
        return transactionService.getAllTransactions();
    }
    @PostMapping
    public Transactions addTransactions(@RequestBody Transactions transactions) {
        return transactionService.addTransaction(transactions);
    }
    @GetMapping("/{id}")
    public Transactions getTransactionById(@PathVariable String id) {
        return transactionService.getTransactionById(id);
    }
    @PutMapping("/{id}")
    public Transactions updateTransactionById(@PathVariable String id, @RequestBody TransactionDTO transactions) {
        return transactionService.updateTransaction(id, transactions);
    }
    @DeleteMapping
    public void deleteTransactionById(@PathVariable String id) {
        transactionService.deleteTransaction(id);
    }
    @PostMapping("/add-list-transactions")
    public List<Transactions> addListOfTransactions(@RequestBody List<Transactions> transactions) {
        return transactionService.addListOfTransactions(transactions);
    }

}
