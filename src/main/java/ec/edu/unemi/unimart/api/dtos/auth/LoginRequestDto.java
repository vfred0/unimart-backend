package ec.edu.unemi.unimart.api.dtos.auth;


public record LoginRequestDto(
        String username,
        String password
) {
}