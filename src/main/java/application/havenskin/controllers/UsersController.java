package application.havenskin.controllers;

import application.havenskin.dataAccess.UserDTO;
import application.havenskin.models.Orders;
import application.havenskin.models.Users;
import application.havenskin.repositories.UserRepository;
import application.havenskin.services.JwtService;
import application.havenskin.services.UsersService;
import com.google.api.client.googleapis.auth.oauth2.GoogleIdToken;
import com.google.api.client.googleapis.auth.oauth2.GoogleIdTokenVerifier;
import com.google.api.client.http.javanet.NetHttpTransport;
import com.google.api.client.json.jackson2.JacksonFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.Optional;


@RestController
@RequestMapping("/haven-skin/users")
public class UsersController {
    private final UsersService usersService;
    private final UserRepository usersRepository;
    private final JwtService jwtService;

    @Autowired
    public UsersController(UsersService usersService, UserRepository usersRepository, JwtService jwtService) {
        this.usersService = usersService;
        this.usersRepository = usersRepository;
        this.jwtService = jwtService;
    }

    @Value("${spring.security.oauth2.client.registration.google.client-id}")
    private String googleClientId;

    // Lấy danh sách tất cả người dùng
    @GetMapping
    public List<Users> getAllUsers() {
        return usersService.getAllUsers();
    }

    // Lấy thông tin người dùng theo ID
    @GetMapping("/{userId}")
    public Users getUserById(@PathVariable String userId) {
        return usersService.getUserById(userId);
    }

    // Tạo người dùng mới
    @PostMapping
    public Users createUser(@RequestBody Users user) {
        return usersService.saveUser(user);
    }

    // Xóa người dùng (chỉ cập nhật trạng thái, không xóa thật)
    @DeleteMapping("/{userId}")
    public void deleteUser(@PathVariable String userId) {
        usersService.deleteUser(userId);
    }

    @PostMapping("/add-list-user")
    public List<Users> addListUser(@RequestBody List<Users> users){
        return usersService.addListOfUsers(users);
    }

    @GetMapping("/admin-staff")
    public List<Users> getAdminAndStaffUsers() {
        return usersService.getAdminAndStaffUsers();
    }

    @GetMapping("/customers")
    public List<Users> getCustomerUsers() {
        return usersService.getCustomerUsers();
    }


    @PostMapping("/login/google")
    public ResponseEntity<?> loginWithGoogle(@RequestBody String credential) {
        try {
            credential = credential.replaceAll("[\\[\\]\"]", "");
            GoogleIdTokenVerifier verifier = new GoogleIdTokenVerifier.Builder(
                    new NetHttpTransport(),
                    JacksonFactory.getDefaultInstance())
                    .setAudience(Collections.singletonList(googleClientId))
                    .build();

            GoogleIdToken idToken = verifier.verify(credential);
            if (idToken == null) {
                return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(Map.of("error", "Invalid Google token"));
            }

            GoogleIdToken.Payload payload = idToken.getPayload();
            Users user = processUser(payload);

            // ✅ Tạo JWT Token
            String token = jwtService.generateToken(user.getEmail(), String.valueOf(user.getRole())); // ✅ Đúng cú pháp

            // ✅ Chuyển đổi sang UserDTO
            UserDTO userDTO = new UserDTO();
            userDTO.setFirstName(user.getFirstName());
            userDTO.setLastName(user.getLastName());
            userDTO.setEmail(user.getEmail());
            userDTO.setRole(user.getRole());

            // ✅ Chuyển hướng FE theo vai trò
            String redirectUrl = switch (user.getRole()) {
                case 1, 2 -> "/admin-dashboard";
                case 3 -> "/";
                default -> null;
            };

            if (redirectUrl == null) {
                return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(Map.of("error", "Role not recognized"));
            }

            return ResponseEntity.ok(Map.of(
                    "token", token,
                    "user", userDTO,
                    "redirectUrl", redirectUrl
            ));

        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(Map.of("error", "Authentication failed: " + e.getMessage()));
        }
    }

    private Users processUser(GoogleIdToken.Payload payload) {
        String email = payload.getEmail();
        String firstName = (String) payload.get("given_name");
        String lastName = (String) payload.get("family_name");

        Optional<Users> userOptional = usersRepository.findByEmail(email);
        return userOptional.orElseGet(() -> {
            Users newUser = new Users();
            newUser.setEmail(email);
            newUser.setFirstName(firstName);
            newUser.setLastName(lastName);
            newUser.setRole((byte) 3);
            return usersRepository.save(newUser);
        });
    }

    // Đăng xuất
    @GetMapping("/logout")
    public String logout() {
        return "You have been logged out successfully!";
    }
}
