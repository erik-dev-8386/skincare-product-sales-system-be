package application.havenskin.Services;

import application.havenskin.BusinessObject.Models.Users;
import application.havenskin.DataAccess.AuthServiceResponseDto;
import application.havenskin.DataAccess.LoginDto;
import application.havenskin.DataAccess.RegisterDto;
import application.havenskin.Repositories.UsersRepository;
import org.springframework.stereotype.Service;

import java.util.Optional;
@Service
public class AuthService {
    private final UsersRepository userRepository;

    public AuthService(UsersRepository userRepository) {
        this.userRepository = userRepository;
    }

    /*public AuthServiceResponseDto login(LoginDto loginDto) {
        Optional<Users> userOptional = userRepository.findByUserName(loginDto.getUserName());
        if (userOptional.isEmpty() || !loginDto.getPassword().equals(userOptional.get().getPassword())) {
            return new AuthServiceResponseDto(false, "Invalid Credentials", (byte) 0);
        }

        Users user = userOptional.get();
        String token = generateToken(user);

        return new AuthServiceResponseDto(true, token, user.getRole());
    }

    public AuthServiceResponseDto register(RegisterDto registerDto) {
        if (userRepository.findByUserName(registerDto.getUserName()).isPresent()) {
            return new AuthServiceResponseDto(false, "UserName Already Exists", (byte) 0);
        }

        byte role = registerDto.getRole();

        Users user = new Users();
        user.setFirstName(registerDto.getFirstName());
        user.setLastName(registerDto.getLastName());
        user.setEmail(registerDto.getEmail());
        user.setUserName(registerDto.getUserName());
        user.setPassword(registerDto.getPassword());
        user.setGender(registerDto.getGender());
        user.setRole(role);

        userRepository.save(user);

        return new AuthServiceResponseDto(true, "User Created Successfully", (byte) 0);
    }

    private String generateToken(Users user) {
        return "dummy-jwt-token";
    }*/
}
