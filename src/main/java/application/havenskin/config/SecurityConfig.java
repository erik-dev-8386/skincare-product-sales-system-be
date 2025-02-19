package application.havenskin.config;

//import application.havenskin.enums.Role;
//import application.havenskin.enums.Role;
import application.havenskin.enums.Role;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.oauth2.jose.jws.MacAlgorithm;
import org.springframework.security.oauth2.jwt.JwtDecoder;
import org.springframework.security.oauth2.jwt.NimbusJwtDecoder;
import org.springframework.security.oauth2.server.resource.authentication.JwtAuthenticationConverter;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

import javax.crypto.spec.SecretKeySpec;
import java.util.Collections;
import java.util.List;

@Configuration
@EnableWebSecurity
@EnableMethodSecurity(prePostEnabled = true)
public class SecurityConfig {
    private static final String SIGN_KEY = "U0Ec+zdBMdxc7lSoSXfeXCKphSZkUT2GIqhHQBxgirb0Psm2uneOCeuV4/K7X46s";
    @Bean
    public SecurityFilterChain filterChain(HttpSecurity httpSecurity) throws Exception{
        httpSecurity.
                authorizeHttpRequests(request ->
                request.requestMatchers(HttpMethod.POST, "/haven-skin/users/login")
                        .permitAll()
                        .requestMatchers(HttpMethod.POST,"/haven-skin/users").permitAll()
                        .requestMatchers(HttpMethod.POST,"/haven-skin/users/login/google").permitAll()
                        .anyRequest().authenticated())
                .csrf(httpSecurityCsrfConfigurer -> httpSecurityCsrfConfigurer.disable())
                .oauth2ResourceServer(httpSecurityOAuth2ResourceServerConfigurer -> httpSecurityOAuth2ResourceServerConfigurer.jwt(jwtConfigurer -> jwtConfigurer.decoder(jwtDecoder()).jwtAuthenticationConverter(jwtAuthenticationConverter())))
                .cors(httpSecurityCorsConfigurer -> httpSecurityCorsConfigurer.configurationSource(corsConfigurationSource()));;;

//        httpSecurity.oauth2ResourceServer(httpSecurityOAuth2ResourceServerConfigurer -> httpSecurityOAuth2ResourceServerConfigurer.jwt(jwtConfigurer -> jwtConfigurer.decoder(jwtDecoder()).jwtAuthenticationConverter(jwtAuthenticationConverter())));
//        csrf(httpSecurityCsrfConfigurer -> httpSecurityCsrfConfigurer.disable());
        //  httpSecurity.cors(httpSecurityCorsConfigurer -> httpSecurityCorsConfigurer.configurationSource(corsConfigurationSource()));
        return httpSecurity.build();
    }

//@Bean
//public SecurityFilterChain filterChain(HttpSecurity httpSecurity) throws Exception {
//    httpSecurity
//            .csrf(csrf -> csrf.disable()) // Tắt CSRF nếu không cần
//            .authorizeHttpRequests(auth -> auth
//                    .requestMatchers(HttpMethod.POST, "/haven-skin/users/login").permitAll()
//                    .requestMatchers(HttpMethod.POST, "/haven-skin/users").permitAll()
//                    .anyRequest().authenticated() // Các request còn lại cần xác thực
//            )
//            .oauth2ResourceServer(oauth2 -> oauth2
//                    .jwt(jwtConfigurer -> jwtConfigurer
//                            .decoder(jwtDecoder()) // Bộ giải mã JWT
//                            .jwtAuthenticationConverter(jwtAuthenticationConverter()) // Chuyển đổi quyền từ JWT
//                    )
//            )
//            .csrf(httpSecurityCsrfConfigurer -> httpSecurityCsrfConfigurer.disable());
//
//    return httpSecurity.build();
//}

    //này tao làm
//    @Bean
//    JwtAuthenticationConverter JWTAuthenticationConverter() {
//        JwtAuthenticationConverter converter = new JwtAuthenticationConverter();
//        converter.setJwtGrantedAuthoritiesConverter(jwt ->{
//            Long x = jwt.getClaim("role");
//            if(x == null){
//                return Collections.emptyList();
//            }
//            try{
//            Role role = Role.fromValue(x.byteValue());
//            String roleName = "ROLE_" + role.name();
//            return Collections.singletonList(new SimpleGrantedAuthority(roleName));
//        }
//            catch (Exception e){
//            return Collections.emptyList();}
//        });
//        return converter;
//    }
    @Bean
    CorsConfigurationSource corsConfigurationSource() {
        CorsConfiguration configuration = new CorsConfiguration();
        configuration.setAllowedOrigins(List.of(
                "http://localhost:5173" // Chỉ chấp nhận yêu cầu từ localhost trên cổng 5173
        ));
        configuration.setAllowedMethods(List.of("GET", "POST", "PUT", "DELETE", "OPTIONS", "PATCH"));
        configuration.setAllowedHeaders(List.of("Authorization", "Cache-Control", "Content-Type"));
        configuration.setAllowCredentials(true); // Cho phép gửi thông tin xác thực
        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        source.registerCorsConfiguration("/**", configuration); // Áp dụng cho tất cả các đường dẫn
        return source;
    }

    @Bean
    JwtAuthenticationConverter jwtAuthenticationConverter() {
        JwtAuthenticationConverter converter = new JwtAuthenticationConverter();
        converter.setJwtGrantedAuthoritiesConverter(jwt ->{
            Long x = (long)jwt.getClaim("role");
            if(x == null){
                return Collections.emptyList();
            }
            try{
                Role role = Role.fromValue(x.byteValue());
                String roleName = "ROLE_" + role.name();
//                Role role = Role.fromValue(x.byteValue());
//                String roleName = "ROLE_" + role.name();
                return Collections.singletonList(new SimpleGrantedAuthority(roleName));
            }
            catch (Exception e){
                return Collections.emptyList();
            }
        });
        return converter;
    }

    @Bean
    JwtDecoder jwtDecoder() {
        SecretKeySpec secretKeySpec = new SecretKeySpec(SIGN_KEY.getBytes(), "HS512");
        return NimbusJwtDecoder.withSecretKey(secretKeySpec).macAlgorithm(MacAlgorithm.HS512).build();
    }

    @Bean
    PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }
}
