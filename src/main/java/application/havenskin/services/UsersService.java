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
//                x.setFirstName(userDTO.getFirstName());
                orders.setCustomerFirstName(userDTO.getFirstName());
            } else {
                throw  new RuntimeException("First name is missing for user with email: " + email);
            }
        }
        if (x.getLastName() == null || userDTO.getLastName() != null) {
            if (userDTO.getLastName() != null) {
                //        x.setLastName(userDTO.getLastName());
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
            //    x.setPhone(userDTO.getPhone());
                orders.setCustomerPhone(userDTO.getPhone());
            } else {
                throw new RuntimeException("Phone number is missing for user with email: " + email);
            }
        }
        if (x.getAddress() == null || userDTO.getAddress() != null) {
            if (userDTO.getAddress() != null) {
            //    x.setAddress(userDTO.getAddress());
                orders.setAddress(userDTO.getAddress());
            } else {
                throw new RuntimeException("Address is missing for user with email: " + email);
            }
        }
//        System.out.println("Saved user: " +x);
//        usersRepository.save(x);
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
        return usersRepository.save(user);
    }

    public Users createUser(UserDTO user) {
//        Users x = mapper.toUsers(user);
//        PasswordEncoder passwordEncoder = new BCryptPasswordEncoder(10);
//        x.setPassword(passwordEncoder.encode(user.getPassword()));
//        return userRepository.save(x);
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
            user.setStatus((byte) 0);
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

            if (file != null && !file.isEmpty()) {
                String avatar = firebaseService.uploadImage(file);
                existingUser.setImage(avatar);
            }
            return usersRepository.save(existingUser);
        }
    }





}