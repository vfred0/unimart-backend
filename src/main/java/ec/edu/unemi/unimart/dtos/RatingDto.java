package ec.edu.unemi.unimart.dtos;

import java.util.UUID;

public record RatingDto(
        UUID id,
        String comment,
        Integer rating
) {
}
