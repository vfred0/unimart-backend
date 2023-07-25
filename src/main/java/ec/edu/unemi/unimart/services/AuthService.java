package ec.edu.unemi.unimart.services;

import ec.edu.unemi.unimart.dtos.login.LoginRequestDto;
import ec.edu.unemi.unimart.dtos.login.LoginResponseDto;
import ec.edu.unemi.unimart.repositories.IUserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class AuthService implements IAuthService {
    private final IUserRepository userRepository;

    @Override
    public Optional<LoginResponseDto> login(LoginRequestDto loginRequestDto) {
        return this.userRepository.findByUsernameAndPassword(loginRequestDto.getUsername(), loginRequestDto.getPassword()).map(user ->
                LoginResponseDto.builder().id(user.getId()).build()
        );
    }
}
