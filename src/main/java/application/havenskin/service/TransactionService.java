package application.havenskin.service;

import application.havenskin.BusinessObject.Models.Transactions;
import application.havenskin.DTORequest.TransactionDTO;
import application.havenskin.mapper.Mapper;
import application.havenskin.repository.TransactionsRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class TransactionService {
    @Autowired
    private TransactionsRepository transactionsRepository;
    @Autowired
    private Mapper mapper;
    public List<Transactions> getAllTransactions() {
        return transactionsRepository.findAll();
    }
    public Transactions getTransactionById(String id) {
        if(!transactionsRepository.existsById(id)) {
            throw new RuntimeException("Transaction not found");
        }
        return transactionsRepository.findById(id).get();
    }
    public Transactions addTransaction(Transactions transaction) {
        return transactionsRepository.save(transaction);
    }
    public Transactions updateTransaction(String id, TransactionDTO transaction) {
       Transactions x = transactionsRepository.findById(id).orElseThrow(() -> new RuntimeException("Transaction not found"));
       mapper.updateTransactions(x, transaction);
       return transactionsRepository.save(x);
    }
    public void deleteTransaction(String id) {
        if(!transactionsRepository.existsById(id)) {
            throw new RuntimeException("Transaction not found");
        }
        transactionsRepository.deleteById(id);
    }
    public List<Transactions> addListOfTransactions(List<Transactions> transactions) {
        return transactionsRepository.saveAll(transactions);
    }
}
