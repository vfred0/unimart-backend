package ec.edu.unemi.unimart.data.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.Arrays;

@Getter
@AllArgsConstructor
public enum Category {
    TEXT_BOOKS_EDUCATIONAL_MATERIAL("Libros texto material educativo"),
    OFFICE_SUPPLIES("Material de oficina"),
    ELECTRONICS("Electrónica"),
    FURNITURE_AND_DECORATION("Mobiliario y decoración"),
    CLOTHING("Indumentaria"),
    LABORATORY_MATERIAL("Material de laboratorio"),
    MUSICAL_INSTRUMENTS("Instrumentos musicales"),
    SPORTING_GOODS("Artículos deportivos"),
    ARTS_HANDICRAFTS("Artes manuales"),
    TOYS_GAMES("Juegos y juguetes");

    private final String name;

    public static Category byName(String category) {
        return Arrays.stream(Category.values()).filter(c -> c.getName().equals(category)).findFirst().orElse(null);
    }
    @Override
    public String toString() {
        return name;
    }
}