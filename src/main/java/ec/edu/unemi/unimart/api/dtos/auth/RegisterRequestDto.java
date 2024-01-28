package ec.edu.unemi.unimart.api.dtos.auth;

import ec.edu.unemi.unimart.data.enums.Role;

import java.util.Set;

public record RegisterRequestDto(
        String names,
        String username,
        String password,
        Set<Role> roles
) {
}
