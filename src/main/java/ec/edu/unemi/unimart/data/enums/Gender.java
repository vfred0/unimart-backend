package ec.edu.unemi.unimart.data.enums;

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

    @Override
    public String toString() {
        return name;
    }
}