package application.havenskin.controllers;

import application.havenskin.dataAccess.*;
import application.havenskin.enums.Role;
import application.havenskin.models.Users;
import application.havenskin.repositories.UserRepository;
import application.havenskin.services.AuthenticationService;
import application.havenskin.services.UserService;
import com.google.api.client.googleapis.auth.oauth2.GoogleIdToken;
import com.google.api.client.googleapis.auth.oauth2.GoogleIdTokenVerifier;
import com.google.api.client.http.javanet.NetHttpTransport;
import com.google.api.client.json.jackson2.JacksonFactory;
import com.nimbusds.jose.JOSEException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

import java.text.ParseException;
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
    @Autowired
    private UserRepository userRepository;
//
//    @Value("${spring.security.oauth2.client.registration.google.client-id}")
//    private String googleClientId;
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

    @PostMapping("/refresh-token")
    public AuthencationResponse refreshToken(@RequestBody RefreshRequest x) throws ParseException, JOSEException {
        return authenticationService.refresh(x);
    }

    // Đăng nhập với Google Credential (thay cho phương thức getUserInfo cũ)
    @PostMapping("/login/google")
    public Users loginWithGoogle(@RequestBody String credential) {
        try {
            System.out.println("Received Token: " + credential); // Debug token

            // Nếu token có dấu [ ], loại bỏ chúng
            if (credential.startsWith("[") && credential.endsWith("]")) {
                credential = credential.substring(1, credential.length() - 1);
            }
            credential = credential.trim().replace("\"", ""); // Loại bỏ dấu " nếu có

            System.out.println("Sanitized Token: " + credential); // Debug token sau khi xử lý

            GoogleIdTokenVerifier verifier = new GoogleIdTokenVerifier.Builder(
                    new NetHttpTransport(),
                    JacksonFactory.getDefaultInstance())
                    .setAudience(Collections.singletonList(googleClientId))
                    .build();

            GoogleIdToken idToken = verifier.verify(credential);
            if (idToken == null) {
                throw new RuntimeException("Invalid Google token");
            }

            GoogleIdToken.Payload payload = idToken.getPayload();
            return processUser(payload);

        } catch (Exception e) {
            e.printStackTrace();
            throw new RuntimeException("Authentication failed: " + e.getMessage());
        }
    }

    private Users processUser(GoogleIdToken.Payload payload) {
        String email = payload.getEmail();
        String firstName = (String) payload.get("given_name");
        String lastName = (String) payload.get("family_name");

        Optional<Users> userOptional = userRepository.findByEmail(email);
        return userOptional.orElseGet(() -> {
            Users newUser = new Users();
            newUser.setEmail(email);
            newUser.setFirstName(firstName);
            newUser.setLastName(lastName);
            newUser.setRole((byte) 3); // CUSTOMER role
            return userRepository.save(newUser);
        });
    }

    // Đăng xuất
    @GetMapping("/logout")
    public String logout() {
        return "You have been logged out successfully!";
    }
}
