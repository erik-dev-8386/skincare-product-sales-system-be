package application.havenskin.config;

import application.havenskin.enums.Role;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.oauth2.client.oidc.web.logout.OidcClientInitiatedLogoutSuccessHandler;
import org.springframework.security.oauth2.client.registration.ClientRegistrationRepository;
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
@EnableGlobalMethodSecurity(prePostEnabled = true)
public class SecurityConfig {
    private static final String SIGN_KEY = "U0Ec+zdBMdxc7lSoSXfeXCKphSZkUT2GIqhHQBxgirb0Psm2uneOCeuV4/K7X46s";

    @Autowired
    private  ClientRegistrationRepository clientRegistrationRepository;

//    public SecurityConfig(ClientRegistrationRepository clientRegistrationRepository) {
//        this.clientRegistrationRepository = clientRegistrationRepository;
//    }

    //    public SecurityConfig(ClientRegistrationRepository clientRegistrationRepository) {
//        this.clientRegistrationRepository = clientRegistrationRepository;
//    }
    private final String[] PUBLIC_ENDPOINTS = {"/haven-skin/brands","/haven-skin/categories", "/haven-skin/discounts", "/haven-skin/products", "/haven-skin/skin-types", "/haven-skin/products/{id}", "/haven-skin/categories/name/{categoryName}","/haven-skin/products/category/{categoryname}","/haven-skin/products/best-seller", "/haven-skin/blogs", "/haven-skin/blogCategory", "/haven-skin/blog-hashtag","/haven-skin/skin-tests/questions-answers/{skintestId}", "/haven-skin/products/{id}", "/haven-skin/products", "/haven-skin/brands", "/haven-skin/categories", "/haven-skin/discounts", "/haven-skin/skin-types", "/haven-skin/reviews/product/{id}","/haven-skin/web-socket/monthly","/haven-skin/ws","/haven-skin/get/salesData","/haven-skin/send/data",  "/haven-skin/feedbacks/{email}/{productName}", "/haven-skin/feedbacks/average-rating/{productName}", "/haven-skin/feedbacks/get-star/by-customer/{productName}", "/haven-skin/blogs/title/{title}"};
    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity httpSecurity) throws Exception {
        httpSecurity.
                authorizeHttpRequests(request ->
                        request.requestMatchers(HttpMethod.POST, "/haven-skin/users/login")
                                .permitAll()
                                .requestMatchers(HttpMethod.POST,"/haven-skin/users/add-new-user").permitAll()
                                .requestMatchers(HttpMethod.POST,"/haven-skin/users/login/google/**").permitAll()
                                .requestMatchers(HttpMethod.POST, "/haven-skin/momo/create/**").permitAll()
                                .requestMatchers(HttpMethod.POST, "/haven-skin/momo/ipn-handler-new").permitAll()
                                .requestMatchers("/haven-skin/momo/**").permitAll() // Bỏ xác thực cho API MoMo
                                .requestMatchers(HttpMethod.GET, PUBLIC_ENDPOINTS).permitAll()
                                .requestMatchers(HttpMethod.POST, "/haven-skin/feedbacks//{email}/{productName}").permitAll()
                                .requestMatchers(HttpMethod.POST, "/haven-skin/cart/checkout").permitAll()
                                .anyRequest().authenticated())
                .csrf(httpSecurityCsrfConfigurer -> httpSecurityCsrfConfigurer.disable())
                .oauth2ResourceServer(httpSecurityOAuth2ResourceServerConfigurer -> httpSecurityOAuth2ResourceServerConfigurer.jwt(jwtConfigurer -> jwtConfigurer.decoder(jwtDecoder()).jwtAuthenticationConverter(jwtAuthenticationConverter())))
                .cors(httpSecurityCorsConfigurer -> httpSecurityCorsConfigurer.configurationSource(corsConfigurationSource()));;;
        return httpSecurity.build();

    }

//    @Bean
//    public SecurityFilterChain securityFilterChain(HttpSecurity httpSecurity) throws Exception {
//        httpSecurity
//                .authorizeHttpRequests(auth -> auth.anyRequest().permitAll()) // Tạm thời mở hết
//                .csrf(csrf -> csrf.disable()); // Tắt CSRF
//        return httpSecurity.build();
//    }


    @Bean
    public CorsConfigurationSource corsConfigurationSource() {
        CorsConfiguration configuration = new CorsConfiguration();
        configuration.setAllowedOrigins(List.of("http://localhost:5173")); // Đảm bảo trùng với FE
        configuration.setAllowedMethods(List.of("GET", "POST", "PUT", "DELETE", "OPTIONS"));
        configuration.setAllowedHeaders(List.of("*"));
        configuration.setAllowCredentials(true);
        configuration.addExposedHeader("Authorization");
        configuration.addExposedHeader("Cross-Origin-Opener-Policy");
        configuration.addExposedHeader("Cross-Origin-Embedder-Policy");

        // Thêm header này để tránh lỗi COOP
        configuration.addExposedHeader("Cross-Origin-Resource-Policy");

        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        source.registerCorsConfiguration("/**", configuration);
        return source;
    }

    @Bean
    PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }
    @Bean
    JwtDecoder jwtDecoder() {
        SecretKeySpec secretKeySpec = new SecretKeySpec(SIGN_KEY.getBytes(), "HS512");
        return NimbusJwtDecoder.withSecretKey(secretKeySpec).macAlgorithm(MacAlgorithm.HS512).build();
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

    /**
     * Xử lý logout thành công cho OIDC
     */
    private OidcClientInitiatedLogoutSuccessHandler oidcLogoutSuccessHandler() {
        OidcClientInitiatedLogoutSuccessHandler handler =
                new OidcClientInitiatedLogoutSuccessHandler(clientRegistrationRepository);
        handler.setPostLogoutRedirectUri("http://localhost:8080/users/login"); // URL sau khi logout
        return handler;
    }
}
