package application.havenskin.service;

import application.havenskin.BusinessObject.Models.Users;
import application.havenskin.repository.UserRepository;
import org.apache.catalina.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserService {
    @Autowired
    private UserRepository userRepository;
    public List<Users> getAllUsers() {
        return userRepository.findAll();
    }
    public Users getUserById(String id) {
        if(!userRepository.existsById(id)) {
            throw new RuntimeException("User not found");
        }
        return userRepository.findById(id).get();
    }

    public Users createUser(Users user) {
        return userRepository.save(user);
    }
    public Users updateUser(String id,Users user) {
        if(!userRepository.existsById(id)) {
            throw new RuntimeException("User not found");
        }
        return userRepository.save(user);
    }
    public void deleteUser(String id) {
        if(!userRepository.existsById(id)) {
            throw new RuntimeException("User not found");
        }
        userRepository.deleteById(id);
    }
}
