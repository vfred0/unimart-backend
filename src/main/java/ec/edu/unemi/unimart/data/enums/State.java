package ec.edu.unemi.unimart.data.enums;

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
    @Override
    public String toString() {
        return name;
    }
}