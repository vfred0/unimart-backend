package ec.edu.unemi.unimart.services.rating;

import ec.edu.unemi.unimart.dtos.RatingDto;
import ec.edu.unemi.unimart.services.crud.ICrudService;

import java.util.UUID;

public interface IRatingService extends ICrudService<RatingDto, UUID> {
}
