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

import java.util.List;
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
}
