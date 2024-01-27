package ec.edu.unemi.unimart.services.rating;

import ec.edu.unemi.unimart.api.dtos.RatingDto;
import ec.edu.unemi.unimart.data.entities.Rating;
import jakarta.transaction.Transactional;

import java.util.List;
import java.util.UUID;

public interface IRatingService {
    @Transactional
    UUID save(RatingDto ratingDto);

    @Transactional
    Rating saveAndUpdateUserDetails(RatingDto ratingDto);

    List<RatingDto> getByUserId(UUID userId);
}
