package ec.edu.unemi.unimart.utils;

public enum TypeArticle {
    PUBLISHED("Publicados"),
    PROPOSED("Propuestos");

    private final String name;

    TypeArticle(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }
}

