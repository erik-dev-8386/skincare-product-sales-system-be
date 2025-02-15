package application.havenskin.controllers;

import application.havenskin.dataAccess.AuthencationRequest;
import application.havenskin.dataAccess.AuthencationResponse;
import application.havenskin.dataAccess.UserDTO;
import application.havenskin.models.Users;
import application.havenskin.services.AuthenticationService;
import application.havenskin.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
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
        return userService.createUser(user);
    }
    @GetMapping("/login")
    public AuthencationResponse login(@RequestBody AuthencationRequest x) {
        return authenticationService.authenticate(x);
    }
    @PostMapping("/add-list-users")
    public List<Users> addListUsers(@RequestBody List<Users> users) {
        return userService.addListUsers(users);
    }
}
