package ec.edu.unemi.unimart.services.auth;

import ec.edu.unemi.unimart.api.dtos.login.LoginRequestDto;
import ec.edu.unemi.unimart.api.dtos.login.LoginResponseDto;
import ec.edu.unemi.unimart.data.daos.IUserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class AuthService implements IAuthService {

    private final IUserRepository userRepository;

    @Override
    public Optional<LoginResponseDto> login(LoginRequestDto loginRequestDto) {
        return Optional.empty();
    }
}
