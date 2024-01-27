package ec.edu.unemi.unimart.data.enums;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

import java.util.Arrays;
import java.util.Collection;

@Getter
@RequiredArgsConstructor
public enum Role {
    ROLE_ADMIN, ROLE_USER;

    private final String name = this.name();
}
