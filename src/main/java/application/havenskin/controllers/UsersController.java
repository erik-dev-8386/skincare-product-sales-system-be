package application.havenskin.controllers;

import application.havenskin.models.Orders;
import application.havenskin.models.Users;
import application.havenskin.repositories.UserRepository;
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

import static application.havenskin.enums.Role.*;

@RestController
@RequestMapping("/haven-skin/users")
public class UsersController {
    private final UsersService usersService;
    private final UserRepository usersRepository;

    @Value("${spring.security.oauth2.client.registration.google.client-id}")
    private String googleClientId;
    // Thêm CLIENT_ID vào application.properties
    //private String googleClientId;

    @Autowired
    public UsersController(UsersService usersService, UserRepository usersRepository) {
        this.usersService = usersService;
        this.usersRepository = usersRepository;
    }

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

    @PostMapping("/login/google")
    public ResponseEntity<?> loginWithGoogle(@RequestBody String credential) {
        try {
            // Xử lý token và xác thực
            credential = credential.replaceAll("[\\[\\]\"]", ""); // Xử lý token JSON dạng ["token"]
            GoogleIdTokenVerifier verifier = new GoogleIdTokenVerifier.Builder(
                    new NetHttpTransport(),
                    JacksonFactory.getDefaultInstance())
                    .setAudience(Collections.singletonList(googleClientId))
                    .build();
            GoogleIdToken idToken = verifier.verify(credential);
            if (idToken == null) {
                return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Invalid Google token");
            }

            GoogleIdToken.Payload payload = idToken.getPayload();
            String email = payload.getEmail();

            // Tìm user theo email (Xử lý Optional)
            Users user = usersRepository.findByEmail(email).orElse(null);
            if (user == null) {
                return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("User not found");
            }

            // Xác định trang home dựa theo role
            String redirectUrl;
            switch (user.getRole()) {
                case 1:
                    redirectUrl = "/admin/home";
                    break;
                case 2:
                    redirectUrl = "/user/home";
                    break;
                case 3:
                    redirectUrl = "/customer/home";
                    break;
                default:
                    return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Role not recognized");
            }

            // Trả về URL phù hợp
            return ResponseEntity.ok().body(Map.of("redirectUrl", redirectUrl));

        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Authentication failed: " + e.getMessage());
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
            newUser.setRole((byte) 2); // CUSTOMER role
            return usersRepository.save(newUser);
        });
    }


    // Đăng xuất
    @GetMapping("/logout")
    public String logout() {
        return "You have been logged out successfully!";
    }
}
