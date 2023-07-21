package ec.edu.unemi.unimart.services.rating;

import ec.edu.unemi.unimart.dtos.RatingDto;
import ec.edu.unemi.unimart.models.Rating;
import ec.edu.unemi.unimart.repositories.IRatingRepository;
import ec.edu.unemi.unimart.services.crud.CrudService;
import ec.edu.unemi.unimart.utils.Mapper;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
public class RatingService extends CrudService<Rating, RatingDto, UUID> implements IRatingService {

    public RatingService(Mapper mapper, IRatingRepository repository) {
        super(mapper, repository, Rating.class, RatingDto.class);
    }
}
