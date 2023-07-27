package ec.edu.unemi.unimart.controllers;

import ec.edu.unemi.unimart.dtos.login.LoginRequestDto;
import ec.edu.unemi.unimart.dtos.login.LoginResponseDto;
import ec.edu.unemi.unimart.services.IAuthService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/auth")
public class AuthController {
    private final IAuthService authService;

    @PostMapping("login")
    public ResponseEntity<LoginResponseDto> login(@RequestBody LoginRequestDto loginRequestDto) {
        return authService.login(loginRequestDto)
                .map(loginResponse -> new ResponseEntity<>(loginResponse, HttpStatus.OK))
                .orElseGet(() -> new ResponseEntity<>(HttpStatus.UNAUTHORIZED));
    }
}
