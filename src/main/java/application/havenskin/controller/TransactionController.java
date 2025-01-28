package application.havenskin.controller;

import application.havenskin.BusinessObject.Models.Transactions;
import application.havenskin.DTORequest.TransactionDTO;
import application.havenskin.response.Response;
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
    public Response<List<Transactions>> getAllTransactions() {
        Response<List<Transactions>> response = new Response<>();
        response.setCode(200);
        response.setMessage("OK");
        response.setResult(transactionService.getAllTransactions());
        return response;
    }
    @PostMapping
    public Response<Transactions> addTransactions(@RequestBody Transactions transactions) {
        Response<Transactions> response = new Response<>();
        response.setCode(200);
        response.setMessage("OK");
        response.setResult(transactionService.addTransaction(transactions));
        return response;
    }
    @GetMapping("/{id}")
    public Response<Transactions> getTransactionById(@PathVariable String id) {
        Response<Transactions> response = new Response<>();
        response.setCode(200);
        response.setMessage("OK");
        response.setResult(transactionService.getTransactionById(id));
        return response;
    }
    @PutMapping("/{id}")
    public Response<Transactions> updateTransactionById(@PathVariable String id, @RequestBody TransactionDTO transactions) {
        Response<Transactions> response = new Response<>();
        response.setCode(200);
        response.setMessage("OK");
        response.setResult(transactionService.updateTransaction(id, transactions));
        return response;
    }
    @DeleteMapping
    public void deleteTransactionById(@PathVariable String id) {
        transactionService.deleteTransaction(id);
    }
    @PostMapping("/add-list-transactions")
    public List<Transactions> addListOfTransactions(@RequestBody List<Transactions> transactions) {
        Response<List<Transactions>> response = new Response<>();
        response.setCode(200);
        response.setMessage("OK");
        response.setResult(transactionService.addListOfTransactions(transactions));
        return transactions;
    }

}
