package ec.edu.unemi.unimart.services.auth;

import ec.edu.unemi.unimart.api.dtos.auth.AccessToken;
import ec.edu.unemi.unimart.api.dtos.auth.LoginRequestDto;
import ec.edu.unemi.unimart.api.dtos.auth.RegisterRequestDto;
import ec.edu.unemi.unimart.data.daos.IUserRepository;
import ec.edu.unemi.unimart.data.daos.IUserRoleRepository;
import ec.edu.unemi.unimart.data.entities.User;
import ec.edu.unemi.unimart.data.entities.UserRole;
import ec.edu.unemi.unimart.data.enums.Role;
import ec.edu.unemi.unimart.services.jwt.JwtAccessTokenService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import java.util.Set;

@Component
@RequiredArgsConstructor
public class AuthService {

    private final AuthenticationManager authenticationManager;
    private final JwtAccessTokenService jwtAccessTokenService;
    private final IUserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final IUserRoleRepository userRoleRepository;

    public AccessToken authenticate(LoginRequestDto loginRequestDto) {
        Authentication authenticationToken = new UsernamePasswordAuthenticationToken(
                loginRequestDto.username(),
                loginRequestDto.password()
        );
        Authentication authentication = this.authenticationManager.authenticate(authenticationToken);

        String idToken = this.jwtAccessTokenService.generateIdToken(authentication);
        return new AccessToken(idToken);
    }

    public void register(RegisterRequestDto registerRequestDto) {
        UserRole userRole = this.userRoleRepository.findByName(Role.ROLE_AUTHENTICATED)
                .orElseThrow(() -> new RuntimeException("User role not found."));

        boolean isUsernameExists = userRepository.findByUsername(registerRequestDto.username()).isPresent();
        if (isUsernameExists) {
            throw new RuntimeException("Username already exists.");
        }

        this.userRepository.save(
                User.builder()
                        .names(registerRequestDto.names())
                        .username(registerRequestDto.username())
                        .password(registerRequestDto.password())
                        .password(passwordEncoder.encode(registerRequestDto.password()))
                        .roles(Set.of(userRole))
                        .build()
        );

    }
}
