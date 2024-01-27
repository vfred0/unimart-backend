package ec.edu.unemi.unimart.api.resources;

import ec.edu.unemi.unimart.api.dtos.login.LoginRequestDto;
import ec.edu.unemi.unimart.api.dtos.login.LoginResponseDto;
import ec.edu.unemi.unimart.services.jwt.JwtService;
import ec.edu.unemi.unimart.services.jwt.JwtUserDetailsService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.DisabledException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("auth")
public class AuthResource {

    private final AuthenticationManager authenticationManager;
    private final JwtService jwtService;
    private final JwtUserDetailsService jwtUserDetailsService;

    @PutMapping("login")
    public ResponseEntity<LoginResponseDto> login(@RequestBody LoginRequestDto loginRequestDto) throws Exception {
        authenticate(loginRequestDto.username(), loginRequestDto.password());
        UserDetails userDetails = jwtUserDetailsService.loadUserByUsername(loginRequestDto.username());
        return new ResponseEntity<>(jwtService.generateToken(userDetails), HttpStatus.OK);
    }

    private void authenticate(String username, String password) throws Exception {
        try {
            authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(username, password));
        } catch (DisabledException e) {
            throw new Exception("USER_DISABLED", e);
        } catch (BadCredentialsException e) {
            throw new Exception("INVALID_CREDENTIALS", e);
        }

    }
}