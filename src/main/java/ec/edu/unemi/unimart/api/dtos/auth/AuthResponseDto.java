package ec.edu.unemi.unimart.api.dtos.auth;

import lombok.Builder;

import java.util.UUID;

@Builder
public record AuthResponseDto(
        UUID userId,
        String token,
        String refreshToken

) {
}
