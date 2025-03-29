    package application.havenskin.services;

    import application.havenskin.dataAccess.TransactionDTO;
    import application.havenskin.enums.OrderEnums;
    import application.havenskin.enums.TransactionsEnums;
    import application.havenskin.mapper.Mapper;
    import application.havenskin.models.Orders;
    import application.havenskin.models.Transactions;
    import application.havenskin.repositories.TransactionsRepository;
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
            Orders order = orderService.getOrderById(orderId);
            Transactions transaction = transactionsRepository.findByOrderId(orderId);
            if (transaction == null) {
                Transactions newTransaction = new Transactions();
                newTransaction.setOrderId(orderId);
                newTransaction.setTransactionStatus(TransactionsEnums.PAID.getValue());
                newTransaction.setTransactionType(TransactionsEnums.Type.COD.getValue());
                newTransaction.setAmount(order.getTotalAmount());
                newTransaction.setTransactionTime(LocalDateTime.now());
                newTransaction.setContent("Thanh toán COD cho đơn hàng: " + orderId);
                transactionsRepository.save(newTransaction);
            }
        }
    }