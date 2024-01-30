package ec.edu.unemi.unimart.api.resources;

import ec.edu.unemi.unimart.api.dtos.auth.AccessToken;
import ec.edu.unemi.unimart.api.dtos.auth.AuthResponseDto;
import ec.edu.unemi.unimart.api.dtos.auth.LoginRequestDto;
import ec.edu.unemi.unimart.api.dtos.auth.RegisterRequestDto;
import ec.edu.unemi.unimart.services.auth.AuthService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@Slf4j
@RequiredArgsConstructor
@RequestMapping("/api/v1/auth")
public class AuthResource {

    private final AuthService authService;

    @PostMapping("login")
    public ResponseEntity<AccessToken> login(@RequestBody LoginRequestDto loginRequestDto) throws Exception {
        log.info("Login request: {}", loginRequestDto);
        return new ResponseEntity<>(authService.authenticate(loginRequestDto), HttpStatus.OK);
    }


    @PostMapping("register")
    public ResponseEntity<Void> register(@RequestBody RegisterRequestDto registerRequestDto) {
        authService.register(registerRequestDto);
        return new ResponseEntity<>(HttpStatus.CREATED);
    }
}