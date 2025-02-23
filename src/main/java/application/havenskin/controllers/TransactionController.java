package application.havenskin.controllers;

import application.havenskin.config.Config;
import application.havenskin.dataAccess.TransactionDTO;
import application.havenskin.enums.TransactionsEnums;
import application.havenskin.enums.OrderEnums;
import application.havenskin.models.Transactions;
import application.havenskin.repositories.TransactionsRepository;
import application.havenskin.services.OrderService;
import application.havenskin.services.TransactionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Map;

@CrossOrigin(origins = "http://localhost:5173")

@RestController
@RequestMapping("/haven-skin/transactions")
public class TransactionController {
    @Autowired
    private TransactionService transactionService;
    @Autowired
    private TransactionsRepository transactionsRepository;
    @Autowired
    private OrderService orderService;

    // Inject TransactionsService
    public TransactionController(TransactionService transactionService, TransactionsRepository transactionsRepository, OrderService orderService) {
        this.transactionService = transactionService;
        this.transactionsRepository = transactionsRepository;
        this.orderService = orderService;
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

    @GetMapping("/return")
    public ResponseEntity<String> vnpReturn(@RequestParam Map<String, String> queryParams, @RequestParam String orderId) {
        try {
            System.out.println("🔹 VNPAY Response: " + queryParams);

            String vnp_ResponseCode = queryParams.get("vnp_ResponseCode");
            String vnp_TxnRef = queryParams.get("vnp_TxnRef");
            String vnp_Amount = queryParams.get("vnp_Amount");
            String vnp_BankCode = queryParams.get("vnp_BankCode");
            String vnp_PayDate = queryParams.get("vnp_PayDate");
            String vnp_SecureHash = queryParams.get("vnp_SecureHash");

            if (vnp_ResponseCode == null || vnp_TxnRef == null) {
                return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Thiếu dữ liệu từ VNPAY");
            }

            // Kiểm tra chữ ký hợp lệ
            String signData = Config.hashAllFields(queryParams);
            if (!signData.equals(vnp_SecureHash)) {
                System.out.println("❌ Chữ ký không hợp lệ!");
                return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Invalid signature");
            }

            boolean isSuccess = "00".equals(vnp_ResponseCode);

            // Lưu giao dịch
            Transactions transaction = new Transactions();
            transaction.setOrderId(orderId);
            transaction.setTransactionCode(vnp_TxnRef);
            transaction.setAmount(Double.parseDouble(vnp_Amount) / 100);
            transaction.setBankName(vnp_BankCode);
            transaction.setTransactionTime(LocalDateTime.parse(vnp_PayDate, DateTimeFormatter.ofPattern("yyyyMMddHHmmss")));
            transaction.setTransactionStatus(isSuccess ? TransactionsEnums.PAID.getValue() : TransactionsEnums.NOT_PAID.getValue());

            transactionsRepository.save(transaction);

            // Cập nhật trạng thái đơn hàng
            if (isSuccess) {
                boolean updated = orderService.updateOrderStatus(orderId, OrderEnums.PROCESSING.getOrder_status());
                if (!updated) {
                    return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Cập nhật trạng thái đơn hàng thất bại");
                }
            }

            return ResponseEntity.ok(isSuccess ? "✅ Payment successful" : "❌ Payment failed");
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Lỗi xử lý giao dịch");
        }
    }

}
