package ec.edu.unemi.unimart.data.enums;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum Role {
    ROLE_ADMIN,
    ROLE_AUTHENTICATED;

    private final String name = this.name();
}
