package ec.edu.unemi.unimart.data.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.Arrays;

@Getter
@AllArgsConstructor
public enum ArticleType {
    PUBLISHED("Publicado"),
    PROPOSED("Propuesto"),
    EXCHANGED("Intercambiado");

    private final String name;
    @Override
    public String toString() {
        return name;
    }

}