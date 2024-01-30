package ec.edu.unemi.unimart.api.dtos.auth;

import ec.edu.unemi.unimart.data.enums.Role;
import jakarta.validation.constraints.Size;

import java.util.Set;

public record RegisterRequestDto(
        @Size(max = 60)
        String names,
        @Size(max = 50)
        String photo,
        String username,
        String password,
        @Size(max = 10)
        String numberWhatsapp,
        @Size(max = 250)
        String about,
        Set<Role> roles
) {
}
