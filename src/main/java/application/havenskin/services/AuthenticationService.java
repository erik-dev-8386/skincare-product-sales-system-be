package application.havenskin.services;

import application.havenskin.dataAccess.AuthencationRequest;
import application.havenskin.dataAccess.AuthencationResponse;
import application.havenskin.models.Users;
import application.havenskin.repositories.UserRepository;
import com.nimbusds.jose.*;
import com.nimbusds.jose.crypto.MACSigner;
import com.nimbusds.jwt.JWTClaimsSet;
import org.apache.logging.log4j.CloseableThreadContext;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.nio.charset.StandardCharsets;
import java.time.Instant;
import java.util.Date;
import java.util.Optional;

@Service
public class AuthenticationService {
    @Autowired
    private UserRepository userRepository;

    private static final String SIGN_KEY = "U0Ec+zdBMdxc7lSoSXfeXCKphSZkUT2GIqhHQBxgirb0Psm2uneOCeuV4/K7X46s";
    public AuthencationResponse authenticate(AuthencationRequest x) {
        AuthencationResponse response = new AuthencationResponse();
        {
            Optional<Users> user = userRepository.findByEmail(x.getEmail());
            if (user.isPresent()) {
                PasswordEncoder passwordEncoder = new BCryptPasswordEncoder(10);
                boolean check = passwordEncoder.matches(x.getPassword(), user.get().getPassword());
                if(!check) {
                    throw new RuntimeException("Wrong password or Wrong username");
                }
                else{
                    response.setToken(generateToken(x.getEmail(), x.getPassword()));
                    response.setStatus(passwordEncoder.matches(x.getPassword(), user.get().getPassword()));
                    return response;
                }
            }
            throw new RuntimeException("Invalid email or password");
        }

    }
    public String generateToken(String email, String password) {
        JWSHeader header = new JWSHeader(JWSAlgorithm.HS512);

        JWTClaimsSet jwtClaimsSet = new JWTClaimsSet.Builder()
                .subject(email)
                .claim("password", password)
                .issuer("HavenSkin.com")
                .issueTime(new Date())
                .expirationTime(new Date(Instant.now().getEpochSecond() + 1000))
                .build();

        Payload payload = new Payload(jwtClaimsSet.toJSONObject());
        JWSObject jwsObject = new JWSObject(header, payload);
        try {
            jwsObject.sign(new MACSigner(SIGN_KEY.getBytes()));
            return jwsObject.serialize();
        } catch (JOSEException e) {
            throw new RuntimeException(e);
        }

    }

}

