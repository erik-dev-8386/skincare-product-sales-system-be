package application.havenskin.services;

import application.havenskin.dataAccess.TransactionDTO;
import application.havenskin.enums.OrderEnums;
import application.havenskin.enums.TransactionsEnums;
import application.havenskin.mapper.Mapper;
import application.havenskin.models.Transactions;
import application.havenskin.repositories.TransactionsRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;

@Service
public class TransactionService {
    @Autowired
    private TransactionsRepository transactionsRepository;
    @Lazy // Trì hoãn khởi tạo OrderService để tránh vòng lặp
    @Autowired
    private OrderService orderService;
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

    public Transactions createTransaction(String orderId, double amount) {
        Transactions transaction = new Transactions();
        transaction.setOrderId(orderId);
        transaction.setAmount(amount);
        transaction.setTransactionType(TransactionsEnums.Type.MOMO.getValue());
        transaction.setTransactionStatus(TransactionsEnums.PAID.getValue());
        transaction.setTransactionTime(LocalDateTime.now());
        return transactionsRepository.save(transaction);
    }

//    public void saveTransactionToDB(String orderId, String transactionCode, String amount, String bankName, String payDate, boolean isSuccess) {
//        try {
//            System.out.println("🔹 Đang lưu giao dịch...");
//            System.out.println("Mã giao dịch: " + transactionCode);
//            System.out.println("Số tiền: " + amount);
//            System.out.println("Ngân hàng: " + bankName);
//            System.out.println("Ngày thanh toán: " + payDate);
//            System.out.println("Trạng thái: " + (isSuccess ? "PAID" : "NOT_PAID"));
//
//
//            Transactions transaction = new Transactions();
//            System.out.println("orderId: " + transaction.getOrderId());
//            transaction.setOrderId(orderId);
//            transaction.setTransactionCode(transactionCode);
//            transaction.setTransactionType((byte) 1);
//            transaction.setAmount(Double.parseDouble(amount) / 100);
//            transaction.setBankName(bankName);
//            transaction.setTransactionTime(LocalDateTime.parse(payDate, DateTimeFormatter.ofPattern("yyyyMMddHHmmss")));
//            transaction.setTransactionStatus(isSuccess ? TransactionsEnums.PAID.getValue() : TransactionsEnums.NOT_PAID.getValue());
//
//            transactionsRepository.save(transaction);
//
//            // Cập nhật trạng thái đơn hàng
//            if (isSuccess) {
//                boolean updated = orderService.updateOrderStatus(orderId, OrderEnums.PROCESSING.getOrder_status());
//                if (!updated) {
//                    System.out.println("Cập nhật trạng thái đơn hàng thất bại");
//                    return;
//                }
//            }
//
//            System.out.println("✅ Giao dịch đã lưu thành công!");
//        } catch (Exception e) {
//            System.out.println("❌ Lỗi khi lưu giao dịch:");
//            e.printStackTrace();
//        }
//    }
}
