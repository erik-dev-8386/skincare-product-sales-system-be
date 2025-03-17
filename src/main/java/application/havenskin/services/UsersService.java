package application.havenskin.services;

import application.havenskin.dataAccess.UserDTO;
import application.havenskin.dataAccess.UserServiceResponseDto;
import application.havenskin.enums.Role;
import application.havenskin.models.Users;
import application.havenskin.repositories.UserRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.*;

@Service
public class UsersService {
    private final UserRepository usersRepository;
    private static final Logger logger = LoggerFactory.getLogger(UsersService.class);

    @Autowired
    private FirebaseService firebaseService;
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

        validateUserProfileForCheckout(user.getEmail());
        return user;
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

    public Users updateUser(String email,  UserDTO user, MultipartFile file) throws IOException {
        String userId = usersRepository.findByEmail(email).get().getUserId();
        if(userId == null) {
            throw new RuntimeException("User not found");
        }
        else{
          Users existingUser = usersRepository.findById(userId).orElseThrow(() -> new RuntimeException("User not found!"));

          existingUser.setFirstName(user.getFirstName());
          existingUser.setLastName(user.getLastName());
          existingUser.setEmail(user.getEmail());
          existingUser.setPhone(user.getPhone());
          existingUser.setGender(user.getGender());
          existingUser.setAddress(user.getAddress());
          existingUser.setBirthDate(user.getBirthDate());

          if(file != null && !file.isEmpty()) {
              String avatar = firebaseService.uploadImage(file);
              existingUser.setImage(avatar);
          }
//          else {
//              existingUser.setImage(null);
//          }
          return usersRepository.save(existingUser);
        }
    }

//    public Users updateUserByOrder(UserDTO user){
//
//    }



}