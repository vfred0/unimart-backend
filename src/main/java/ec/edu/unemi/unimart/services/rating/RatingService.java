package ec.edu.unemi.unimart.services.rating;

import ec.edu.unemi.unimart.dtos.RatingDto;
import ec.edu.unemi.unimart.exceptions.MessageException;
import ec.edu.unemi.unimart.exceptions.NotFoundException;
import ec.edu.unemi.unimart.models.Rating;
import ec.edu.unemi.unimart.models.User;
import ec.edu.unemi.unimart.repositories.IRatingRepository;
import ec.edu.unemi.unimart.services.user.IUserService;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class RatingService implements IRatingService {

    private final IRatingRepository ratingRepository;
    private final IUserService userService;

    @Override
    public UUID save(RatingDto ratingDto) {
        User userWhoRated = this.userService.getUserById(ratingDto.getUserIdWhoRated());
        User userWhoWasRated = this.userService.getUserById(ratingDto.getUserIdWhoWasRated());

        Rating rating = Rating.builder()
                .comment(ratingDto.getComment())
                .score(ratingDto.getScore())
                .build();

        rating.setUserWhoRated(userWhoRated);
        rating.setUserWhoWasRated(userWhoWasRated);

        return this.ratingRepository.save(rating).getId();
    }

    @Override
    @Transactional
    public Rating saveAndUpdateUserDetails(RatingDto ratingDto) {
        UUID id = this.save(ratingDto);
        Rating rating = this.ratingRepository.findById(id).orElseThrow(() -> NotFoundException.throwBecauseOf(MessageException.RATING_NOT_FOUND));
        User userWhoRated = rating.getUserWhoRated();
        User userWhoWasRated = rating.getUserWhoWasRated();
        userWhoWasRated.setAverageRating(rating);
        userWhoWasRated.updateNumberExchanges();
        userWhoRated.updateNumberExchanges();
        return rating;
    }

    @Override
    public List<RatingDto> getByUserId(UUID userId) {
        User user = this.userService.getUserById(userId);
        return user.getWhereUserWhoWasRated().stream().map(Rating::getDetails).toList();
    }

}
