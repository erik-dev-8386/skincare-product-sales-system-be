package application.havenskin.config;

import application.havenskin.enums.Role;
import application.havenskin.models.Users;
import application.havenskin.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.ApplicationRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.crypto.password.PasswordEncoder;

@Configuration
public class ApplicationInitConfig {
    @Autowired
    PasswordEncoder passwordEncoder;
    @Bean
    ApplicationRunner applicationRunner(UserRepository userRepository){// duoc khoi chay khi moi lan app start len
        return args -> {
            if(userRepository.findByEmail("admin@gmail.com").isEmpty()){
                Users x = new Users();
                x.setEmail("admin@gmail.com");
                x.setPassword(passwordEncoder.encode("123456"));
                x.setRole(Role.ADMIN.getValue());
                x.setFirstName("Admin");
                x.setLastName("Admin");
                userRepository.save(x);
            }
        };
    }
}
