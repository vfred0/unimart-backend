package ec.edu.unemi.unimart.data.enums;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.ToString;

@Getter
@ToString
@RequiredArgsConstructor
public enum Role {
    ROLE_ADMIN,
    ROLE_AUTHENTICATED;

    private final String name = this.name();

    public static void main(String[] args) {
        System.out.println(Role.ROLE_ADMIN.getName());
    }
}
