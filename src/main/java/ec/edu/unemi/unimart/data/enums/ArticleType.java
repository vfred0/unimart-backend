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

    public static ArticleType byName(String typeArticle) {
        return Arrays.stream(ArticleType.values()).filter(ta -> ta.getName().equals(typeArticle)).findFirst().orElse(null);
    }

    public static boolean isExchanged(ArticleType articleType) {
        return articleType.equals(EXCHANGED);
    }

    @Override
    public String toString() {
        return name;
    }

}