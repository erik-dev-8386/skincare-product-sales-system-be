package application.havenskin.service;

import application.havenskin.BusinessObject.Models.Users;
import application.havenskin.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class UserService {
    @Autowired
    private UserRepository userRepository;

    public List<Users> getAllUsers() {
        return userRepository.findAll();
    }

    public Users getUsersById(String id) {
        return userRepository.findById(id).get();
    }

    public Users createUser(Users user) {
        return userRepository.save(user);
    }

    public Users updateUser(String id, Users user) {
        Users userToUpdate = getUsersById(id);
        userToUpdate.setFirstName(user.getFirstName());
        userToUpdate.setLastName(user.getLastName());
        userToUpdate.setEmail(user.getEmail());
        userToUpdate.setAddress(user.getAddress());
        userToUpdate.setPhoneNumber(user.getPhoneNumber());
        userToUpdate.setGender(user.getGender());
        userToUpdate.setBirthDate(user.getBirthDate());
        userToUpdate.setImage(user.getImage());
        userToUpdate.setFeedbacks(user.getFeedbacks());
        userToUpdate.setRating(user.getRating());
        return userRepository.save(userToUpdate);
    }

    public void deleteUser(String id) {
        userRepository.deleteById(id);
    }
}
