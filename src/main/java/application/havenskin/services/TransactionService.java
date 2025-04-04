    package application.havenskin.services;

    import application.havenskin.dataAccess.TransactionDTO;
    import application.havenskin.enums.OrderEnums;
    import application.havenskin.enums.TransactionsEnums;
    import application.havenskin.mapper.Mapper;
    import application.havenskin.models.Orders;
    import application.havenskin.models.Transactions;
    import application.havenskin.models.Users;
    import application.havenskin.repositories.OrdersRepository;
    import application.havenskin.repositories.TransactionsRepository;
    import application.havenskin.repositories.UserRepository;
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
            List<Orders> orders = ordersRepository.findByOrderId(orderId);
            Optional<Users> users = userRepository.findById(orders.get(0).getUserId());
            String email = users.get().getEmail();
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
                order.setStatus(OrderEnums.PENDING.getOrder_status());
                transactionsRepository.save(newTransaction);
             //   sendOrderConfirmationEmail(email, orderId, order.getTotalAmount());
            }
        }
//        private void sendOrderConfirmationEmail(String to, String orderId, double totalAmount) {
//            // Tiêu đề email
//            String subject = "Haven Skin - Xác nhận thanh toán thành công đơn hàng #" + orderId;
//
//            // Nội dung email được format đẹp
////    String emailContent =
////            "=============================================\n" +
////                    "               HAVEN SKIN                    \n" +
////                    "=============================================\n\n" +
////                    "          XÁC NHẬN THANH TOÁN THÀNH CÔNG          \n\n" +
////                    "Cảm ơn bạn đã đặt hàng tại Haven Skin!\n" +
////                    "Đơn hàng của bạn đã được thanh toán thành công.\n\n" +
////                    "---------------------------------------------\n" +
////                    "THÔNG TIN ĐƠN HÀNG:\n" +
////                    "---------------------------------------------\n" +
////                    "• Mã đơn hàng: #" + orderId + "\n" +
////                    "• Tổng tiền:   " + String.format("%,.0f VND", totalAmount) + "\n\n" +
////                    "---------------------------------------------\n" +
////                    "Nếu có bất kỳ thắc mắc nào, vui lòng liên hệ:\n" +
////                    "• Hotline: 0966340303\n" +
////                    "• Email: havenskin032025@gmail.com\n\n" +
////                    "Trân trọng,\n" +
////                    "Đội ngũ Haven Skin\n" +
////                    "=============================================";
//            String emailContent =
//                    "╔══════════════════════════════════════════╗\n" +
//                            "║              HAVEN SKIN                                                                       ║\n" +
//                            "╠══════════════════════════════════════════╣\n" +
//                            "║     XÁC NHẬN THANH TOÁN THÀNH CÔNG                                  ║\n" +
//                            "╚══════════════════════════════════════════╝\n\n" +
//                            "Cảm ơn quý khách đã lựa chọn Haven Skin!\n" +
//                            "Đơn hàng của bạn đã được xử lý thành công.\n\n" +
//
//                            "┌──────────────────────────────────────────┐\n" +
//                            "│          THÔNG TIN ĐƠN HÀNG                                                         │\n" +
//                            "├──────────────────────────────────────────┤\n" +
//                            "│  ▪ Mã đơn hàng: #" + String.format("%-36s", orderId) +"         │\n" +
//                            "│  ▪ Tổng thanh toán: " + String.format("%,.0f VND%-15s", totalAmount,"                                                        │") + "\n" +
//                            "└──────────────────────────────────────────┘\n\n" +
//
//                            "Hỗ trợ khách hàng:\n" +
//                            "• Hotline: 0966 340 303 (8:00-21:00)\n" +
//                            "• Email: havenskin032025@gmail.com\n" +
//                            "• Website: localhost:5173/\n\n" +
//
//                            "════════════════════════════════════════════\n" +
//                            "Trân trọng,\n" +
//                            "Đội ngũ chăm sóc khách hàng Haven Skin\n" +
//                            "════════════════════════════════════════════";
//            // Gửi email
//            emailService.sendEmail(to, subject, emailContent);
//        }

    }