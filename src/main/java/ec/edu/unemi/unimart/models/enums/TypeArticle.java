package ec.edu.unemi.unimart.models.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.Arrays;

@Getter
@AllArgsConstructor
public enum TypeArticle {
    PUBLISHED("Publicado"),
    PROPOSED("Propuesto");

    private final String name;

    public static TypeArticle byName(String typeArticle) {
        return Arrays.stream(TypeArticle.values()).filter(ta -> ta.getName().equals(typeArticle)).findFirst().orElse(null);
    }

    @Override
    public String toString() {
        return name;
    }

}