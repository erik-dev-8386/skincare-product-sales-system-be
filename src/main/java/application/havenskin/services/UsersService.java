package application.havenskin.services;

import application.havenskin.dataAccess.UserDTO;
import application.havenskin.dataAccess.UserServiceResponseDto;
import application.havenskin.enums.Role;
import application.havenskin.models.Orders;
import application.havenskin.models.Users;
import application.havenskin.repositories.OrderDetailsRepository;
import application.havenskin.repositories.OrdersRepository;
import application.havenskin.repositories.UserRepository;
import org.checkerframework.checker.units.qual.A;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.*;

@Service
public class UsersService {
    private final UserRepository usersRepository;
    private static final Logger logger = LoggerFactory.getLogger(UsersService.class);

    @Autowired
    private FirebaseService firebaseService;

    @Autowired
    private OrdersRepository ordersRepository;

    @Autowired
    private OrderDetailsRepository orderDetailsRepository;

    @Autowired
    private EmailService emailService;

    public UsersService(UserRepository usersRepository) {
        this.usersRepository = usersRepository;
    }

    public List<Users> getAllUsers() {
        return usersRepository.findAll();
    }

    public Users getUserById(String userId) {
        return usersRepository.findById(userId).orElse(null);
    }

    public Users getUserByEmail(String email) {
        return usersRepository.findByEmail(email).orElseThrow(() -> new RuntimeException("User not found"));
    }

    public Users getUserByEmailCheckOut(String email) {
        Users user = usersRepository.findByEmail(email).orElseThrow(() -> new RuntimeException("User not found"));
//
//        validateUserProfileForCheckout(user.getEmail());
        return user;
    }

    //  **********************************************
    // hàm này để khách hàng xác nhận thông tin
    public Orders checkOutUser(String email, String orderId, UserDTO userDTO) {
        Users x = getUserByEmail(email);
//        Orders orders = ordersRepository.findById(orderId).orElseThrow(() -> new RuntimeException("Order not found"));
        Orders orders = ordersRepository.findByOrderIdAndUserId(orderId, x.getUserId()).orElseThrow(() -> new RuntimeException("Order not found"));
        if (x.getFirstName() == null || userDTO.getFirstName() != null) {
            if (userDTO.getFirstName() != null) {
                String firstNameRegrex = "^[a-zA-ZÀ-ỹ(][a-zA-ZÀ-ỹ0-9 '\\-.,&+/()]*$";
                if(!userDTO.getFirstName().matches(firstNameRegrex)) {
                    throw new RuntimeException("Chữ cái đầu trong tên của bạn không được là ký tự đặc biệt! Bạn vui lòng thử lại:");
                }
//                x.setFirstName(userDTO.getFirstName());
                orders.setCustomerFirstName(userDTO.getFirstName());
            } else {
                throw new RuntimeException("First name is missing for user with email: " + email);
            }
        }
        if (x.getLastName() == null || userDTO.getLastName() != null) {
            if (userDTO.getLastName() != null) {
                String lastNameRegrex = "^[a-zA-ZÀ-ỹ(][a-zA-ZÀ-ỹ0-9 '\\-.,&+/()]*$";
                if(!userDTO.getLastName().matches(lastNameRegrex)) {
                    throw new RuntimeException("Chữ cái đầu trong tên của bạn không được là ký tự đặc biệt! Bạn vui lòng thử lại:");
                }
                orders.setCustomerLastName(userDTO.getLastName());
            } else {
                throw new RuntimeException("Last name is missing for user with email: " + email);
            }
        }
        if (x.getEmail() == null || userDTO.getEmail() != null) {
            if (userDTO.getEmail() != null) {
                x.setEmail(userDTO.getEmail());
            } else {
                throw new RuntimeException("Email is missing for user with email: " + email);
            }
        }
        if (x.getPhone() == null || userDTO.getPhone() != null) {
            if (userDTO.getPhone() != null) {
                String phoneRegex = "^[0-9]{10}$";
                if(!userDTO.getPhone().matches(phoneRegex)) {
                    throw new RuntimeException("Phone number must be 10 digits with no special character or letters");
                }
                orders.setCustomerPhone(userDTO.getPhone());
            } else {
                throw new RuntimeException("Phone number is missing for user with email: " + email);
            }
        }
        if (x.getAddress() == null || userDTO.getAddress() != null) {
            if (userDTO.getAddress() != null) {
                if(userDTO.getAddress().length() <= 5){
                    throw new RuntimeException("Address must be at least 5 characters long");
                }
                orders.setAddress(userDTO.getAddress());
            } else {
                throw new RuntimeException("Address is missing for user with email: " + email);
            }
        }
        ordersRepository.save(orders);
        return orders;
    }

    public Map<String, String> validateUserProfileForCheckout(String email) {
        Users user = getUserByEmail(email);
        Map<String, String> validationErrors = new HashMap<>();

        if (user.getAddress() == null || user.getAddress().trim().isEmpty()) {
            validationErrors.put("address", "Vui lòng cập nhật địa chỉ");
        }

        if (user.getPhone() == null || user.getPhone().trim().isEmpty()) {
            validationErrors.put("phone", "Vui lòng cập nhật số điện thoại");
        }

        if ((user.getFirstName() == null || user.getFirstName().trim().isEmpty()) ||
                (user.getLastName() == null || user.getLastName().trim().isEmpty())) {
            validationErrors.put("name", "Vui lòng cập nhật họ và tên");
        }

        return validationErrors;
    }

    public Users saveUser(Users user) {
        user.setRole(user.getRole());
        BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder(10);
        String rawPassword = "123456";
        user.setPassword(passwordEncoder.encode(rawPassword));

        sendAccountCreationEmail(user,rawPassword);
        return usersRepository.save(user);
    }
    private void sendAccountCreationEmail(Users user, String rawPassword) {
        String to = user.getEmail();
        String subject = "Haven Skin - Tạo tài khoản thành công";
        String emailContent =
                "╔════════════════════════════════════════════════════╗\n" +
                        "║                   HAVEN SKIN - CHÀO MỪNG BẠN                  ║\n" +
                        "╠══════════════════════════════════════════════════════════════╣\n" +
                        "║     TÀI KHOẢN CỦA BẠN ĐÃ ĐƯỢC TẠO THÀNH CÔNG!                ║\n" +
                        "╚══════════════════════════════════════════════════════════════╝\n\n" +

                        "Xin chào " + user.getLastName() + " " + user.getFirstName() + ",\n\n" +
                        "Cảm ơn bạn đã đăng ký tài khoản tại Haven Skin. Dưới đây là thông tin tài khoản của bạn:\n\n" +

                        "┌──────────────────────────────────────────────────────────────┐\n" +
                        "│                  THÔNG TIN TÀI KHOẢN                         │\n" +
                        "├──────────────────────────────────────────────────────────────┤\n" +
                        "│  • Họ và tên: " + String.format("%-45s", user.getLastName() + " " + user.getFirstName()) + "│\n" +
                        "│  • Email đăng nhập: " + String.format("%-40s", user.getEmail()) + "│\n" +
                        "│  • Mật khẩu: " + String.format("%-47s", rawPassword) + "│\n" +
                        "└──────────────────────────────────────────────────────────────┘\n\n" +

                        "LƯU Ý QUAN TRỌNG:\n" +
                        "   - Vui lòng đổi mật khẩu ngay sau khi đăng nhập lần đầu.\n" +
                        "   - Không chia sẻ thông tin đăng nhập với bất kỳ ai\n\n" +

                        "HỖ TRỢ KHÁCH HÀNG:\n" +
                        "   • Hotline: 0966 340 303 (8:00 - 21:00 hàng ngày)\n" +
                        "   • Email: havenskin032025@gmail.com\n" +

                        "══════════════════════════════════════════════════════════════\n" +
                        "Trân trọng,\n" +
                        "Đội ngũ chăm sóc khách hàng Haven Skin\n" +
                        "══════════════════════════════════════════════════════════════";

        emailService.sendEmail(to, subject, emailContent);
    }
//    private void sendAccountCreationEmail(Users user, String rawPassword) {
//        String to = user.getEmail();
//        String subject = "Haven Skin - Tạo tài khoản thành công";
//        String emailContent =
//                "╔══════════════════════════════════════════╗\n" +
//                        "║              HAVEN SKIN                 ║\n" +
//                        "╠══════════════════════════════════════════╣\n" +
//                        "║     TẠO TÀI KHOẢN THÀNH CÔNG             ║\n" +
//                        "╚══════════════════════════════════════════╝\n\n" +
//                        "Xin chào " + user.getLastName() + " " + user.getFirstName() + ",\n\n" +
//                        "Tài khoản của bạn tại Haven Skin đã được tạo thành công.\n\n" +
//
//                        "┌──────────────────────────────────────────┐\n" +
//                        "│          THÔNG TIN TÀI KHOẢN         " +
//                        "    │\n" +
//                        "├──────────────────────────────────────────┤\n" +
//                        "│  ▪ Họ và tên: " + String.format("%-30s", user.getLastName() + " " + user.getFirstName()) +
//                        "│\n" +
//                        "│  ▪ Email: " + String.format("%-34s", user.getEmail()) +
//
//                        "│\n" +
//                        "│  ▪ Mật khẩu: " + String.format("%-32s", rawPassword) +
//
//                        "│\n" +
//                        "└──────────────────────────────────────────┘\n\n" +
//
//                        "Lưu ý: Vui lòng đổi mật khẩu sau khi đăng nhập.\n\n" +
//
//                        "Hỗ trợ khách hàng:\n" +
//                        "• Hotline: 0966 340 303 (8:00-21:00)\n" +
//                        "• Email: havenskin032025@gmail.com\n" +
//
//                        "════════════════════════════════════════════\n" +
//                        "Trân trọng,\n" +
//                        "Đội ngũ chăm sóc khách hàng Haven Skin\n" +
//                        "════════════════════════════════════════════";
//
//        emailService.sendEmail(to, subject, emailContent);
//    }

    public Users createUser(UserDTO user) {
        Users x = new Users();
        x.setFirstName(user.getFirstName());
        x.setLastName(user.getLastName());
        x.setPassword(new BCryptPasswordEncoder(10).encode(user.getPassword()));
        x.setEmail(user.getEmail());
        x.setPhone(user.getPhone());
        x.setRole(Role.CUSTOMER.getValue());
        return usersRepository.save(x);
    }

    public List<Users> addListOfUsers(List<Users> users) {
        return usersRepository.saveAll(users);
    }

    public UserServiceResponseDto getById(String id) {
        Optional<Users> userOptional = usersRepository.findById(id);

        return userOptional.map(users -> new UserServiceResponseDto(true, "User found.", Collections.singletonList(users))).orElseGet(() -> new UserServiceResponseDto(false, "No user found for the given user ID.", Collections.emptyList()));
    }

    public UserServiceResponseDto deleteUser(String id) {
        UserServiceResponseDto response = new UserServiceResponseDto();

        try {
            Optional<Users> userOptional = usersRepository.findById(id);
            if (userOptional.isEmpty()) {
                response.setSucceed(false);
                response.setMessage("User not found.");
                return response;
            }

            // Cập nhật trạng thái người dùng
            Users user = userOptional.get();
            user.setStatus((byte) 1);
            usersRepository.save(user);

            response.setSucceed(true);
            response.setMessage("User status updated to inactive (deleted).");
        } catch (Exception ex) {
            logger.error("Error updating user status: ", ex);
            response.setSucceed(false);
            response.setMessage("An error occurred: " + ex.getMessage());
        }

        return response;
    }

    public List<Users> getAdminAndStaffUsers() {
        return usersRepository.findByRoleIn(List.of((byte) 1, (byte) 2));
    }

    public List<Users> getCustomerUsers() {
        return usersRepository.findByRole((byte) 3);
    }

    public Users updateUser(String email, UserDTO user, MultipartFile file) throws IOException {
        String userId = usersRepository.findByEmail(email).get().getUserId();
        if (userId == null) {
            throw new RuntimeException("User not found");
        } else {
            Users existingUser = usersRepository.findById(userId).orElseThrow(() -> new RuntimeException("User not found!"));

            existingUser.setFirstName(user.getFirstName());
            existingUser.setLastName(user.getLastName());
            existingUser.setEmail(user.getEmail());
            existingUser.setPhone(user.getPhone());
            existingUser.setGender(user.getGender());
            existingUser.setAddress(user.getAddress());
            existingUser.setBirthDate(user.getBirthDate());
//
            existingUser.setRole(user.getRole() == 0 ? existingUser.getRole() : user.getRole());
            existingUser.setStatus(user.getStatus() == 0 ? existingUser.getStatus() : user.getStatus());

            if (file != null && !file.isEmpty()) {
                String avatar = firebaseService.uploadImage(file);
                existingUser.setImage(avatar);
            } else {
                existingUser.setImage(null);
            }
            return usersRepository.save(existingUser);
        }
    }

    public void ChangePassword(String email, String passwordOld, String passwordNew) {
        Users x = getUserByEmail(email);
        BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();
        boolean matches = encoder.matches(passwordOld, x.getPassword());
        if (matches) {
            x.setPassword(encoder.encode(passwordNew));
            usersRepository.save(x);
        } else {
            throw new RuntimeException("Wrong password!");
        }
    }


}