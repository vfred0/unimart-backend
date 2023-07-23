package ec.edu.unemi.unimart.models.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.Arrays;
@Getter
@AllArgsConstructor
public enum State  {
    NEW("Nuevo"),
    PREOWNED("Seminuevo"),
    USED("Usado"),
    LOW_QUALITY("Baja calidad");

    private final String name;

    public static State byName(String state) {
        return Arrays.stream(State.values()).filter(s -> s.getName().equals(state)).findFirst().orElse(null);
    }

    @Override
    public String toString() {
        return name;
    }
}