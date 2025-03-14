package application.havenskin.controllers;

import application.havenskin.dataAccess.TransactionDTO;
import application.havenskin.models.Transactions;
import application.havenskin.repositories.TransactionsRepository;
import application.havenskin.services.TransactionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@CrossOrigin(origins = "http://localhost:5173")

@RestController
@RequestMapping("/haven-skin/transactions")
public class TransactionController {
    @Autowired
    private TransactionService transactionService;
    @Autowired
    private TransactionsRepository transactionsRepository;

    // Inject TransactionsService
    public TransactionController(TransactionService transactionService, TransactionsRepository transactionsRepository) {
        this.transactionService = transactionService;
        this.transactionsRepository = transactionsRepository;
    }

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
