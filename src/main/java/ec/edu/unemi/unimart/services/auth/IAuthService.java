package ec.edu.unemi.unimart.services.auth;

import ec.edu.unemi.unimart.dtos.login.LoginRequestDto;
import ec.edu.unemi.unimart.dtos.login.LoginResponseDto;

import java.util.Optional;

public interface IAuthService {

    Optional<LoginResponseDto> login(LoginRequestDto loginRequestDto);
}
