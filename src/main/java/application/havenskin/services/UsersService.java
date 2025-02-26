package application.havenskin.services;

import application.havenskin.dataAccess.UserDTO;
import application.havenskin.dataAccess.UserServiceResponseDto;
import application.havenskin.enums.Role;
import application.havenskin.models.Users;
import application.havenskin.repositories.UserRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.List;
import java.util.Optional;

@Service
public class UsersService {
    private final UserRepository usersRepository;
    private static final Logger logger = LoggerFactory.getLogger(UsersService.class);

    public UsersService(UserRepository usersRepository) {
        this.usersRepository = usersRepository;
    }

    public List<Users> getAllUsers() {
        return usersRepository.findAll();
    }

    public Users getUserById(String userId) {
        return usersRepository.findById(userId).orElse(null);
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


}