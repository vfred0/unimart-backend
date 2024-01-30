package ec.edu.unemi.unimart.services.auth;

import ec.edu.unemi.unimart.api.dtos.auth.AccessToken;
import ec.edu.unemi.unimart.api.dtos.auth.LoginRequestDto;
import ec.edu.unemi.unimart.api.dtos.auth.RegisterRequestDto;
import ec.edu.unemi.unimart.data.daos.IUserAccountRepository;
import ec.edu.unemi.unimart.data.daos.IUserRepository;
import ec.edu.unemi.unimart.data.daos.IUserRoleRepository;
import ec.edu.unemi.unimart.data.entities.identity.UserAccount;
import ec.edu.unemi.unimart.data.entities.identity.UserRole;
import ec.edu.unemi.unimart.data.enums.Role;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import java.util.HashSet;
import java.util.Set;

@Component
@RequiredArgsConstructor
public class AuthService {

    private final AuthenticationManager authenticationManager;
    private final JwtAccessTokenService jwtAccessTokenService;
    private final IUserAccountRepository accountRepository;
    private final PasswordEncoder passwordEncoder;
    private final IUserRoleRepository userRoleRepository;
    private final IUserRepository userRepository;

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
        this.checkIfUsernameExists(registerRequestDto.username());
        saveUserAccount(registerRequestDto);
        saveUser(registerRequestDto);
    }

    private void saveUser(RegisterRequestDto registerRequestDto) {
        this.userRepository.save(
                ec.edu.unemi.unimart.data.entities.User.builder()
                        .names(registerRequestDto.names())
                        .photo(registerRequestDto.photo())
                        .numberWhatsapp(registerRequestDto.numberWhatsapp())
                        .about(registerRequestDto.about())
                        .build()
        );
    }

    private void saveUserAccount(RegisterRequestDto registerRequestDto) {
        Set<UserRole> userRoles = getUserRoles(registerRequestDto);
        this.accountRepository.save(
                UserAccount.builder()
                        .username(registerRequestDto.username())
                        .password(registerRequestDto.password())
                        .password(passwordEncoder.encode(registerRequestDto.password()))
                        .roles(userRoles)
                        .build()
        );
    }

    private Set<UserRole> getUserRoles(RegisterRequestDto registerRequestDto) {
        Set<UserRole> userRoles = new HashSet<>();
        for (Role role : registerRequestDto.roles()) {
            UserRole userRole = this.userRoleRepository.findByName(role)
                    .orElseThrow(() -> new RuntimeException("User role not found."));
            userRoles.add(userRole);
        }
        return userRoles;
    }

    private void checkIfUsernameExists(String username) {
        boolean isUsernameExists = accountRepository.findByUsername(username).isPresent();
        if (isUsernameExists) {
            throw new RuntimeException("Username already exists.");
        }
    }
}
