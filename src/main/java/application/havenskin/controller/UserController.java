package application.havenskin.controller;

import application.havenskin.BusinessObject.Models.Users;
import application.havenskin.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/haven-skin/users")
public class UserController {
    @Autowired
    private UserService userService;
    @GetMapping
    public List<Users> getAllUser() {
        return userService.getAllUsers();
    }
    @PostMapping
    public Users createUser(@RequestBody Users user) {
      return userService.createUser(user);
    }
}
