package ec.edu.unemi.unimart.services.rating;

import ec.edu.unemi.unimart.dtos.RatingDto;
import ec.edu.unemi.unimart.models.Rating;

import java.util.List;
import java.util.UUID;

public interface IRatingService  {
    List<RatingDto> getByUserId(UUID userId);

    UUID save(RatingDto ratingDto);

    Rating saveAndUpdateUserDetails(RatingDto ratingDto);

}
