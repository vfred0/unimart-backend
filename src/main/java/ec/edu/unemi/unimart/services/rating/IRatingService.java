package ec.edu.unemi.unimart.services.rating;

import ec.edu.unemi.unimart.dtos.rating.RatingCardDto;
import ec.edu.unemi.unimart.dtos.rating.RatingDto;
import ec.edu.unemi.unimart.services.crud.ICrudService;

import java.util.List;
import java.util.UUID;

public interface IRatingService extends ICrudService<RatingDto, UUID> {
    List<RatingCardDto> getByUserId(UUID userId);
}
