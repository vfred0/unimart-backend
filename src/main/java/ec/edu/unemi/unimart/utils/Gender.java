package ec.edu.unemi.unimart.utils;

public enum Gender {
    MALE("Masculino"),
    FEMALE("Femenino");

    private final String name;

    Gender(String name) {
        this.name = name;
    }

    String getName() {
        return name;
    }
}
