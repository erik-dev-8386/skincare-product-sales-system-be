package application.havenskin.controllers;

import application.havenskin.dataAccess.AuthencationRequest;
import application.havenskin.dataAccess.AuthencationResponse;
import application.havenskin.dataAccess.UserDTO;
import application.havenskin.enums.Role;
import application.havenskin.models.Users;
import application.havenskin.services.AuthenticationService;
import application.havenskin.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

import java.util.Collections;
import java.util.List;
import java.util.Optional;

@CrossOrigin(origins = "http://localhost:5173")
@RestController
@RequestMapping("/haven-skin/users")
public class UserController {
    @Autowired
    private UserService userService;
    @Autowired
    private AuthenticationService authenticationService;

    @GetMapping
    public List<Users> getAllUser() {
        return userService.getAllUsers();
    }
    @PostMapping
    public Users createUser(@RequestBody UserDTO user) {
        Users newUser = new Users();
        PasswordEncoder passwordEncoder = new BCryptPasswordEncoder(10);
        newUser.setFirstName(user.getFirstName());
        newUser.setLastName(user.getLastName());
        newUser.setEmail(user.getEmail());
        newUser.setPassword(passwordEncoder.encode(user.getPassword()));
        newUser.setRole(Role.CUSTOMER.getValue());
        return userService.createUser(user);
    }
    @PostMapping("/login")
    public AuthencationResponse login(@RequestBody AuthencationRequest x) {
        return authenticationService.authenticate(x);
    }
    @PostMapping("/add-list-users")
    public List<Users> addListUsers(@RequestBody List<Users> users) {
        return userService.addListUsers(users);
    }
//    // Đăng nhập với Google Credential (thay cho phương thức getUserInfo cũ)
//    @PostMapping("/login/google")
//    public Users loginWithGoogle(@RequestBody String credential) {
//        try {
//            System.out.println("Received Token: " + credential); // Debug token
//
//            // Nếu token có dấu [ ], loại bỏ chúng
//            if (credential.startsWith("[") && credential.endsWith("]")) {
//                credential = credential.substring(1, credential.length() - 1);
//            }
//            credential = credential.trim().replace("\"", ""); // Loại bỏ dấu " nếu có
//
//            System.out.println("Sanitized Token: " + credential); // Debug token sau khi xử lý
//
//            GoogleIdTokenVerifier verifier = new GoogleIdTokenVerifier.Builder(
//                    new NetHttpTransport(),
//                    JacksonFactory.getDefaultInstance())
//                    .setAudience(Collections.singletonList(googleClientId))
//                    .build();
//
//            GoogleIdToken idToken = verifier.verify(credential);
//            if (idToken == null) {
//                throw new RuntimeException("Invalid Google token");
//            }
//
//            GoogleIdToken.Payload payload = idToken.getPayload();
//            return processUser(payload);
//
//        } catch (Exception e) {
//            e.printStackTrace();
//            throw new RuntimeException("Authentication failed: " + e.getMessage());
//        }
//    }
//
//    private Users processUser(GoogleIdToken.Payload payload) {
//        String email = payload.getEmail();
//        String firstName = (String) payload.get("given_name");
//        String lastName = (String) payload.get("family_name");
//
//        Optional<Users> userOptional = usersRepository.findByEmail(email);
//        return userOptional.orElseGet(() -> {
//            Users newUser = new Users();
//            newUser.setEmail(email);
//            newUser.setFirstName(firstName);
//            newUser.setLastName(lastName);
//            newUser.setRole((byte) 3); // CUSTOMER role
//            return usersRepository.save(newUser);
//        });
//    }
//
//    // Đăng xuất
//    @GetMapping("/logout")
//    public String logout() {
//        return "You have been logged out successfully!";
//    }
}
