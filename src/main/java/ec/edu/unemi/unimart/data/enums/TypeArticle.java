package ec.edu.unemi.unimart.data.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.Arrays;

@Getter
@AllArgsConstructor
public enum TypeArticle {
    PUBLISHED("Publicado"),
    PROPOSED("Propuesto"),
    EXCHANGED("Intercambiado");

    private final String name;

    public static TypeArticle byName(String typeArticle) {
        return Arrays.stream(TypeArticle.values()).filter(ta -> ta.getName().equals(typeArticle)).findFirst().orElse(null);
    }

    public static boolean isExchanged(TypeArticle typeArticle) {
        return typeArticle.equals(EXCHANGED);
    }

    @Override
    public String toString() {
        return name;
    }

}