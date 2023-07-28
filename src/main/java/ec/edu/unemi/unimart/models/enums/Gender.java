package ec.edu.unemi.unimart.models.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.Arrays;

@Getter
@AllArgsConstructor
public enum Gender {
    MALE("Masculino"),
    FEMALE("Femenino"),
    UNISEX("Unisex");

    private final String name;

    public static Gender byName(String gender) {
        return Arrays.stream(Gender.values()).filter(g -> g.getName().equals(gender)).findFirst().orElse(null);
    }

    @Override
    public String toString() {
        return name;
    }
}