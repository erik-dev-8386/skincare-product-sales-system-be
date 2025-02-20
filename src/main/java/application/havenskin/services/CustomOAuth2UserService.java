package application.havenskin.services;

import application.havenskin.enums.Role;
import application.havenskin.models.Users;
import application.havenskin.repositories.UserRepository;
import jakarta.transaction.Transactional;
import org.springframework.security.oauth2.client.userinfo.DefaultOAuth2UserService;
import org.springframework.security.oauth2.client.userinfo.OAuth2UserRequest;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.stereotype.Service;

@Service
public class CustomOAuth2UserService extends DefaultOAuth2UserService {

    private final UserRepository usersRepository;

    // Constructor để inject UserRepository
    public CustomOAuth2UserService(UserRepository usersRepository) {
        this.usersRepository = usersRepository;
    }

    @Override
    @Transactional
    public OAuth2User loadUser(OAuth2UserRequest userRequest) {
        OAuth2User oAuth2User = super.loadUser(userRequest);

        if (oAuth2User == null) {
            throw new RuntimeException("Failed to retrieve user information from OAuth2 provider");
        }

        String email = oAuth2User.getAttribute("email");
        if (email == null) {
            throw new RuntimeException("Email not found in OAuth2 response");
        }

        System.out.println("OAuth2 Login - Checking user with email: " + email);

        Users user = usersRepository.findByEmail(email).orElseGet(() -> {
            Users newUser = new Users();
            newUser.setEmail(email);
            newUser.setFirstName(oAuth2User.getAttribute("given_name"));
            newUser.setLastName(oAuth2User.getAttribute("family_name"));
            newUser.setRole(Role.CUSTOMER.getValue());

            Users savedUser = usersRepository.save(newUser);
            System.out.println("New user created: " + savedUser);
            return savedUser;
        });

        return oAuth2User;
    }
}