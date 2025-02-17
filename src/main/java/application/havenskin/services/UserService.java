package application.havenskin.services;

import application.havenskin.dataAccess.UserDTO;
import application.havenskin.mapper.Mapper;
import application.havenskin.models.Users;
import application.havenskin.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserService {
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private Mapper mapper;
    public List<Users> getAllUsers() {
        return userRepository.findAll();
    }
    public Users getUserById(String id) {
        if(!userRepository.existsById(id)) {
            throw new RuntimeException("User not found");
        }
        return userRepository.findById(id).get();
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
        return userRepository.save(x);
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

    public List<Users> addListUsers(List<Users> users) {
        return userRepository.saveAll(users);
    }
}
