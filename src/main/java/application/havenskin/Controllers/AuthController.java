package application.havenskin.Controllers;

import application.havenskin.DataAccess.AuthServiceResponseDto;
import application.havenskin.DataAccess.LoginDto;
import application.havenskin.DataAccess.RegisterDto;
import application.havenskin.Services.AuthService;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/auth")
public class AuthController {
/*
    private final AuthService authService;

    public AuthController(AuthService authService) {
        this.authService = authService;
    }

    @PostMapping("/register")
    public ResponseEntity<AuthServiceResponseDto> register(@Validated @RequestBody RegisterDto registerDto) {
        AuthServiceResponseDto response = authService.register(registerDto);
        if (response.isSucceed()) {
            return ResponseEntity.ok(response);
        }
        return ResponseEntity.badRequest().body(response);
    }

    @PostMapping("/login")
    public ResponseEntity<AuthServiceResponseDto> login(@Validated @RequestBody LoginDto loginDto) {
        AuthServiceResponseDto response = authService.login(loginDto);
        if (response.isSucceed()) {
            return ResponseEntity.ok(response);
        }
        return ResponseEntity.status(401).body(response);
    }*/
}
