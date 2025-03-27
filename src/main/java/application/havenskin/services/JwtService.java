package application.havenskin.services;

import application.havenskin.enums.Role;
import com.nimbusds.jose.*;
import com.nimbusds.jose.crypto.MACSigner;
import com.nimbusds.jwt.JWTClaimsSet;
import io.jsonwebtoken.*;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import lombok.experimental.NonFinal;
import org.springframework.stereotype.Service;

import java.security.Key;
import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;
import java.util.function.Function;

@Service
public class JwtService {
//    private static final String SECRET_KEY = "U0Ec+zdBMdxc7lSoSXfeXCKphSZkUT2GIqhHQBxgirb0Psm2uneOCeuV4/K7X46s";

    @NonFinal
    private static final String SECRET_KEY = "U0Ec+zdBMdxc7lSoSXfeXCKphSZkUT2GIqhHQBxgirb0Psm2uneOCeuV4/K7X46s";

    private Key getSigningKey() {
        byte[] keyBytes = Decoders.BASE64.decode(SECRET_KEY);
        return Keys.hmacShaKeyFor(keyBytes);
    }

//    public String generateToken(String email, byte role) {
//        Map<String, Object> claims = new HashMap<>();
////        claims.put("role", role);
////        byte roleValue = Role.valueOf(role).getValue();
//        claims.put("role", role); // Sử dụng giá trị số byte roleValue = Role.valueOf(role).getValue();
////        claims.put("role", roleValue); // Sử dụng giá trị số
//        return Jwts.builder()
//                .setClaims(claims)
//                .setSubject(email)
//                .setIssuedAt(new Date(System.currentTimeMillis()))
//                .setExpiration(new Date(System.currentTimeMillis() + 1000 * 60 * 60)) // 1 giờ
//                .signWith(getSigningKey())
//                .compact();
//    }


public String generateToken(String email, byte role) {
    try {
        // Create JWT header with HS512 algorithm
        JWSHeader header = new JWSHeader.Builder(JWSAlgorithm.HS512)
                .type(JOSEObjectType.JWT)
                .build();

        // Create claims with role as byte
        Map<String, Object> claims = new HashMap<>();
        claims.put("role", role);

        // Build JWT claims set
        JWTClaimsSet claimsSet = new JWTClaimsSet.Builder()
                .subject(email)
                .issuer("HavenSkin")
                .issueTime(new Date())
                .expirationTime(Date.from(Instant.now().plus(1, ChronoUnit.HOURS)))
                .jwtID(UUID.randomUUID().toString()) // Unique ID for token
                .claim("role", role)
                .build();

        // Create JWS object and sign it
        JWSObject jwsObject = new JWSObject(header, new Payload(claimsSet.toJSONObject()));
        jwsObject.sign(new MACSigner(SECRET_KEY.getBytes()));

        // Serialize to compact form
        return jwsObject.serialize();
    } catch (JOSEException e) {
        throw new RuntimeException("Failed to generate token", e);
    }
}

    public String extractEmail(String token) {
        return extractClaim(token, Claims::getSubject);
    }

    public <T> T extractClaim(String token, Function<Claims, T> claimsResolver) {
        final Claims claims = extractAllClaims(token);
        return claimsResolver.apply(claims);
    }

    private Claims extractAllClaims(String token) {
        JwtParser parser = Jwts.parser().build();

        Jws<Claims> claimsJws = parser.parseClaimsJws(token);
        return claimsJws.getBody();
    }

    public boolean isTokenValid(String token, String email) {
        final String extractedEmail = extractEmail(token);
        return (extractedEmail.equals(email) && !isTokenExpired(token));
    }

    private boolean isTokenExpired(String token) {
        return extractClaim(token, Claims::getExpiration).before(new Date());
    }
}
