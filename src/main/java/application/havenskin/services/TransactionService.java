package application.havenskin.services;

import application.havenskin.dataAccess.TransactionDTO;
import application.havenskin.enums.OrderEnums;
import application.havenskin.enums.ProductEnums;
import application.havenskin.enums.TransactionsEnums;
import application.havenskin.mapper.Mapper;
import application.havenskin.models.*;
import application.havenskin.repositories.*;
import jakarta.transaction.Transactional;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Optional;

@Slf4j
@Service
public class TransactionService {
    @Autowired
    private TransactionsRepository transactionsRepository;
    @Lazy
    @Autowired
    private OrderService orderService;
    @Autowired
    private Mapper mapper;
    @Autowired
    private EmailService emailService;
    @Autowired
    private OrdersRepository ordersRepository;
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private OrderDetailsRepository orderDetailsRepository;
    @Autowired
    private ProductsRepository productsRepository;

    public List<Transactions> getAllTransactions() {
        return transactionsRepository.findAll();
    }

    public Transactions getTransactionById(String id) {
        if (!transactionsRepository.existsById(id)) {
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
        if (!transactionsRepository.existsById(id)) {
            throw new RuntimeException("Transaction not found");
        }
        transactionsRepository.deleteById(id);
    }

    public List<Transactions> addListOfTransactions(List<Transactions> transactions) {
        return transactionsRepository.saveAll(transactions);
    }

    @Transactional
    public Transactions createTransaction(String orderId, double amount, byte status, String code) {
        // Kiểm tra transaction đã tồn tại chưa
        Optional<Transactions> existingTransaction = transactionsRepository.findByTransactionCode(code);

        if (existingTransaction.isPresent()) {
            log.info("Transaction với code {} đã tồn tại, không tạo mới", code);
            return existingTransaction.get();
        }

        Transactions transaction = new Transactions();
        transaction.setOrderId(orderId);
        transaction.setBankName("Momo");
        transaction.setContent("Thanh toan cho don hang: " + orderId);
        transaction.setTransactionCode(code);
        transaction.setAmount(amount);
        transaction.setTransactionType(TransactionsEnums.Type.MOMO.getValue());
        transaction.setTransactionStatus(status);
        transaction.setTransactionTime(LocalDateTime.now());

        return transactionsRepository.save(transaction);
    }

    public void successTransactions(String orderId) {
        //        Orders orders = ordersRepository.findById(orderId).orElseThrow(() -> new RuntimeException("Order not found"));
        List<Orders> orders = ordersRepository.findByOrderId(orderId);
        Optional<Users> users = userRepository.findById(orders.get(0).getUserId());
        String email = users.get().getEmail();
        Orders order = orderService.getOrderById(orderId);
        List<OrderDetails> orderDetails = orderDetailsRepository.findByOrderId(orderId);
        Transactions transaction = transactionsRepository.findByOrderId(orderId);
        if (transaction == null) {
            Transactions newTransaction = new Transactions();
            newTransaction.setOrderId(orderId);
            newTransaction.setTransactionStatus(TransactionsEnums.PAID.getValue());
            newTransaction.setTransactionType(TransactionsEnums.Type.COD.getValue());
            newTransaction.setAmount(order.getTotalAmount());
            newTransaction.setTransactionTime(LocalDateTime.now());
            newTransaction.setContent("Thanh toán COD cho đơn hàng: " + orderId);
            order.setStatus(OrderEnums.PENDING.getOrder_status());
            transactionsRepository.save(newTransaction);
            for (OrderDetails orderDetail : orderDetails) {
                Products products = productsRepository.findById(orderDetail.getProductId()).orElseThrow(() -> new RuntimeException("Product not found"));
                products.setQuantity(products.getQuantity() - orderDetail.getQuantity());
                products.setSoldQuantity(products.getSoldQuantity() + orderDetail.getQuantity());
                if(products.getQuantity() <= 0) {
                    products.setStatus(ProductEnums.OUT_OF_STOCK.getValue());
                }
                productsRepository.save(products);
            }
            //   sendOrderConfirmationEmail(email, orderId, order.getTotalAmount());
        }
    }

}