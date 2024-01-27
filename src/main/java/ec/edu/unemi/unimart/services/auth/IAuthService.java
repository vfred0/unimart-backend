package ec.edu.unemi.unimart.services.auth;

import ec.edu.unemi.unimart.api.dtos.login.LoginRequestDto;
import ec.edu.unemi.unimart.api.dtos.login.LoginResponseDto;

import java.util.Optional;

public interface IAuthService {

    Optional<LoginResponseDto> login(LoginRequestDto loginRequestDto);
}
