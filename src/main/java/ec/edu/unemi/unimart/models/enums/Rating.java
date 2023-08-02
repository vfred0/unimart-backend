package ec.edu.unemi.unimart.models.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.Arrays;

@Getter
@AllArgsConstructor
public enum Rating {
    ONE_STAR(1),
    TWO_STARS(2),
    THREE_STARS(3),
    FOUR_STARS(4),
    FIVE_STARS(5);
    private final Short score;

    Rating(Integer i) {
        this.score = Short.parseShort(i.toString());
    }
    public static Rating byScore(Short score) {
        return Arrays.stream(Rating.values()).filter(ta -> ta.getScore().equals(score)).findFirst().orElse(null);
    }

    @Override
    public String toString() {
        return score.toString();
    }
}
