package ec.edu.unemi.unimart.services.rating;

import ec.edu.unemi.unimart.dtos.RatingDto;
import ec.edu.unemi.unimart.models.Rating;
import ec.edu.unemi.unimart.repositories.IRatingRepository;
import ec.edu.unemi.unimart.repositories.IUserRepository;
import ec.edu.unemi.unimart.services.crud.CrudService;
import ec.edu.unemi.unimart.mappers.Mapper;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
public class RatingService extends CrudService<Rating, RatingDto, UUID> implements IRatingService {
    //    private final IRatingMapper ratingMapper;
    private final IUserRepository userRepository;

    public RatingService(Mapper mapper, IRatingRepository repository, IUserRepository userRepository) {
        super(mapper, repository, Rating.class, RatingDto.class);
        this.userRepository = userRepository;
    }

    @Override
    protected IRatingRepository getRepository() {
        return (IRatingRepository) super.getRepository();
    }

    @Override
    public RatingDto save(RatingDto ratingDto) {
        Rating rating = Rating.builder()
                .score(ratingDto.getScore())
                .comment(ratingDto.getComment())
                .build();
        return this.getMapper().toDto(this.getRepository().save(rating), RatingDto.class);
    }

    public List<RatingDto> getByUserId(UUID userId) {
        List<Rating> ratings = this.getRepository().findByUserId(userId);
        return ratings.stream().map(rating ->
                RatingDto.builder()
                        .userName(rating.getUserWhoRated().getName())
                        .userPhoto(rating.getUserWhoRated().getPhoto())
                        .comment(rating.getComment())
                        .score(rating.getScore())
                        .date(rating.getDate())
                        .build()
        ).toList();
    }
}
