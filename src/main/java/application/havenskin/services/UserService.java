package application.havenskin.services;

import application.havenskin.dataAccess.UserDTO;
import application.havenskin.dataAccess.UserServiceResponseDto;
import application.havenskin.enums.Role;
import application.havenskin.mapper.Mapper;
import application.havenskin.models.Users;
import application.havenskin.repositories.UserRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.List;
import java.util.Optional;
@Slf4j
@Service
public class UserService {
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private Mapper mapper;
    public List<Users> getAllUsers() {
        return userRepository.findAll();
    }
    public Users getUserById(String id) {
        if(!userRepository.existsById(id)) {
            throw new RuntimeException("User not found");
        }
        return userRepository.findById(id).get();
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
        return userRepository.save(x);
    }
    public Users updateUser(String id,Users user) {
        if(!userRepository.existsById(id)) {
            throw new RuntimeException("User not found");
        }
        return userRepository.save(user);
    }
//    public void deleteUser(String id) {
//        if(!userRepository.existsById(id)) {
//            throw new RuntimeException("User not found");
//        }
//        userRepository.deleteById(id);
//    }

    public List<Users> addListUsers(List<Users> users) {
        return userRepository.saveAll(users);
    }
    public UserServiceResponseDto getById(String id) {
        Optional<Users> userOptional = userRepository.findById(id);

        return userOptional.map(users -> new UserServiceResponseDto(true, "User found.", Collections.singletonList(users))).orElseGet(() -> new UserServiceResponseDto(false, "No user found for the given user ID.", Collections.emptyList()));
    }

    public UserServiceResponseDto deleteUser(String id) {
        UserServiceResponseDto response = new UserServiceResponseDto();

        try {
            Optional<Users> userOptional = userRepository.findById(id);
            if (userOptional.isEmpty()) {
                response.setSucceed(false);
                response.setMessage("User not found.");
                return response;
            }

            // Cập nhật trạng thái người dùng
            Users user = userOptional.get();
            user.setStatus((byte) 0);
            userRepository.save(user);

            response.setSucceed(true);
            response.setMessage("User status updated to inactive (deleted).");
        } catch (Exception ex) {
            log.error("Error updating user status: ", ex);
            response.setSucceed(false);
            response.setMessage("An error occurred: " + ex.getMessage());
        }

        return response;
    }

}
