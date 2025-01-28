package application.havenskin.controller;

import application.havenskin.BusinessObject.Models.Users;
import application.havenskin.response.Response;
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
    public Response<List<Users>> getAllUser() {
        Response<List<Users>> response = new Response<>();
        response.setCode(200);
        response.setMessage("OK");
        response.setResult(userService.getAllUsers());
        return response;
    }
    @PostMapping
    public Response<Users> createUser(@RequestBody Users user) {
        Response<Users> response = new Response<>();
        response.setCode(200);
        response.setMessage("OK");
        response.setResult(userService.createUser(user));
        return response;
    }
}
