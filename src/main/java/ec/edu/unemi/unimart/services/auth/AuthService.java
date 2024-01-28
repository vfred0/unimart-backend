package ec.edu.unemi.unimart.services.auth;

import ec.edu.unemi.unimart.api.dtos.auth.AuthResponseDto;
import ec.edu.unemi.unimart.api.dtos.auth.LoginRequestDto;
import ec.edu.unemi.unimart.api.dtos.auth.RegisterRequestDto;
import ec.edu.unemi.unimart.data.daos.ISessionRepository;
import ec.edu.unemi.unimart.data.daos.IUserRepository;
import ec.edu.unemi.unimart.data.entities.RoleEntity;
import ec.edu.unemi.unimart.data.entities.Session;
import ec.edu.unemi.unimart.data.entities.User;
import ec.edu.unemi.unimart.data.enums.TokenType;
import ec.edu.unemi.unimart.services.jwt.JwtService;
import ec.edu.unemi.unimart.services.jwt.JwtUserDetailsService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.DisabledException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.Set;

@Service
@Slf4j
@RequiredArgsConstructor
public class AuthService {
    private final IUserRepository userRepository;
    private final ISessionRepository sessionRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtService jwtService;
    private final AuthenticationManager authenticationManager;
    private final JwtUserDetailsService jwtUserDetailsService;

    public AuthResponseDto register(RegisterRequestDto registerRequestDto) {
        String token = jwtService.createToken(registerRequestDto);
        log.info("User for register: {}", registerRequestDto);
        log.info("Token for register: {}", token);
        User user = userRepository.save(getUser(registerRequestDto));
        String refreshToken = jwtService.createRefreshToken(user);
        return getAuthResponseDto(user, token, refreshToken);
    }

    private User getUser(RegisterRequestDto registerRequestDto) {
        Set<RoleEntity> roleEntities = new HashSet<>();
        for (ec.edu.unemi.unimart.data.enums.Role role : registerRequestDto.roles()) {
            roleEntities.add(RoleEntity.builder().name(role).build());
        }
        return User.builder()
                .names(registerRequestDto.names())
                .username(registerRequestDto.username())
                .password(passwordEncoder.encode(registerRequestDto.password()))
                .roles(roleEntities)
                .build();
    }

    private void saveSession(User user, String token) {
        Session session = Session.builder()
                .user(user)
                .tokenValue(token)
                .tokenType(TokenType.BEARER)
                .expired(false)
                .revoked(false)
                .build();
        sessionRepository.save(session);
    }

    public AuthResponseDto authenticate(LoginRequestDto loginRequestDto) throws Exception {
        checkAuthenticate(loginRequestDto);
        UserDetails userDetails = jwtUserDetailsService.loadUserByUsername(loginRequestDto.username());
        String token = jwtService.createToken(userDetails);
        User user = userRepository.findByUsername(loginRequestDto.username()).orElseThrow();
        String refreshToken = jwtService.createRefreshToken(user);
        return getAuthResponseDto(user, token, refreshToken);
    }

    private AuthResponseDto getAuthResponseDto(User user, String token, String refreshToken) {
        saveSession(user, token);
        return AuthResponseDto.builder()
                .userId(user.getId())
                .token(token)
                .refreshToken(refreshToken)
                .build();
    }

    private void checkAuthenticate(LoginRequestDto loginRequestDto) throws Exception {
        try {
            authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(loginRequestDto.username(), loginRequestDto.password()));
        } catch (DisabledException e) {
            throw new Exception("USER_DISABLED", e);
        } catch (BadCredentialsException e) {
            throw new Exception("INVALID_CREDENTIALS", e);
        }

    }

}
