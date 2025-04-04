package application.havenskin.config;

import application.havenskin.enums.Role;
import application.havenskin.models.SkinTests;
import application.havenskin.models.Users;
import application.havenskin.repositories.SkinTestsRepository;
import application.havenskin.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.ApplicationRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.Date;

@Configuration
public class ApplicationInitConfig {
    @Autowired
    PasswordEncoder passwordEncoder;
    @Autowired
    private SkinTestsRepository skinTestsRepository;
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
                x.setStatus((byte) 2);
                userRepository.save(x);
            }
            if(userRepository.findByEmail("staff@gmail.com").isEmpty()){
                Users x = new Users();
                x.setEmail("staff@gmail.com");
                x.setPassword(passwordEncoder.encode("123456"));
                x.setRole(Role.STAFF.getValue());
                x.setFirstName("Staff");
                x.setLastName("Staff");
                x.setStatus((byte) 2);
                userRepository.save(x);
            }
            if(skinTestsRepository.findById("1").isEmpty()){
                SkinTests x = new SkinTests();
                x.setSkinTestId("1");
                x.setTestName("Bài kiểm tra xác định loại da");
                x.setCreatedTime(new Date());
                skinTestsRepository.save(x);
            }
        };
    }
}
