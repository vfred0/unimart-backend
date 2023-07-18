package ec.edu.unemi.unimart.utils;

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

    Category(String displayName) {
        this.name = displayName;
    }

    public String getName() {
        return name;
    }
}

