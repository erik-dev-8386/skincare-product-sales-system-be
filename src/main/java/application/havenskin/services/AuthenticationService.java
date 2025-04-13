package application.havenskin.services;

import application.havenskin.dataAccess.*;
import application.havenskin.models.Users;
import application.havenskin.repositories.UserRepository;
import com.nimbusds.jose.*;
import com.nimbusds.jose.crypto.MACSigner;
import com.nimbusds.jose.crypto.MACVerifier;
import com.nimbusds.jwt.JWTClaimsSet;
import com.nimbusds.jwt.SignedJWT;
import lombok.experimental.NonFinal;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.text.ParseException;
import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.Date;
import java.util.Optional;
import java.util.UUID;

@Service
public class AuthenticationService {
    @Autowired
    private UserRepository userRepository;

    @NonFinal
    private static final String SIGN_KEY = "U0Ec+zdBMdxc7lSoSXfeXCKphSZkUT2GIqhHQBxgirb0Psm2uneOCeuV4/K7X46s";
    public AuthencationResponse authenticate(AuthencationRequest x) {
        AuthencationResponse response = new AuthencationResponse();
        {
            Optional<Users> user = userRepository.findByEmail(x.getEmail());
            if (user.isPresent() && user.get().getStatus() == 2) {
                PasswordEncoder passwordEncoder = new BCryptPasswordEncoder(10);
                boolean check = passwordEncoder.matches(x.getPassword(), user.get().getPassword());
                if(!check) {
                    throw new RuntimeException("Wrong password or Wrong username");
                }
                else{
                    response.setToken(generateToken(user.get().getEmail(), user.get().getRole()));
                    response.setStatus(passwordEncoder.matches(x.getPassword(), user.get().getPassword()));
                    return response;
                }
            }
            throw new RuntimeException("Invalid email or password");
        }
    }

    public String generateToken(String email, byte role) {
        JWSHeader header = new JWSHeader(JWSAlgorithm.HS512);

        JWTClaimsSet jwtClaimsSet = new JWTClaimsSet.Builder()
                .subject(email)
                .issuer("HavenSkin.com")
                .issueTime(new Date())
                .expirationTime(new Date(Instant.now().plus(1, ChronoUnit.HOURS).toEpochMilli()))
                .jwtID(UUID.randomUUID().toString())
                // lưu id để khi hết hạn thì => xử lý
                // nó là duy nhất!
                .claim("role",role)
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
    public void logout(LogOutRequest request) throws ParseException, JOSEException {
        var signToken = verifyToken(request.getToken());

        String jit = signToken.getJWTClaimsSet().getJWTID();

        Date expiryTime = signToken.getJWTClaimsSet().getExpirationTime();

        InvalidatedToken invalidatedToken = InvalidatedToken.builder()
                .id(jit)
                .expiryTime(expiryTime)
                .build();
    }

    public AuthencationResponse refresh
     (RefreshRequest request) throws ParseException, JOSEException {
        // kiem tra hieu luc cua token
        var signJWT = verifyToken(request.getToken());

        var jit = signJWT.getJWTClaimsSet().getJWTID();

        var expiryTime = signJWT.getJWTClaimsSet().getExpirationTime();

        var gmail = signJWT.getJWTClaimsSet().getSubject();

        var x = userRepository.findByEmail(gmail).orElseThrow(()->new RuntimeException("User not found"));

        var newtoken = generateToken(x.getEmail(), x.getRole());

        AuthencationResponse response = new AuthencationResponse();
        response.setToken(newtoken);
        response.setStatus(true);
        return response;

    }
    public SignedJWT verifyToken(String token) throws JOSEException , ParseException {
        JWSVerifier verifier = new MACVerifier(SIGN_KEY.getBytes());

        SignedJWT signedJWT = SignedJWT.parse(token);

        Date expiryTime = signedJWT.getJWTClaimsSet().getExpirationTime();

        var verfied = signedJWT.verify(verifier);

        if(!(verfied && expiryTime.after(new Date()))) {
            throw new RuntimeException("Invalid token");
        }
        return signedJWT;
    }
}

