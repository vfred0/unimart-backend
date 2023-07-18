package ec.edu.unemi.unimart.utils;

public enum State {
    NEW("Nuevo"),
    PREOWNED("Seminuevo"),
    USED("Usado"),
    LOW_QUALITY("Baja calidad");

    private final String name;

    State(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }
}
