package ec.edu.unemi.unimart.services.rating;

import ec.edu.unemi.unimart.dtos.RatingDto;
import ec.edu.unemi.unimart.models.Rating;
import ec.edu.unemi.unimart.models.User;
import ec.edu.unemi.unimart.repositories.IRatingRepository;
import ec.edu.unemi.unimart.repositories.IUserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class RatingService implements IRatingService {
    private final IRatingRepository ratingRepository;
    private final IUserRepository userRepository;

    @Override
    public UUID save(RatingDto ratingDto) {
        Rating rating = Rating.builder()
                .comment(ratingDto.getComment())
                .score(ratingDto.getScore())
                .build();

        User userWhoRated = this.userRepository.findById(ratingDto.getUserIdWhoRated()).orElseThrow();
        User userWhoWasRated = this.userRepository.findById(ratingDto.getUserIdWhoWasRated()).orElseThrow();

        rating.setUserIdWhoRated(userWhoRated);
        rating.setUserIdWhoWasRated(userWhoWasRated);

        return this.ratingRepository.save(rating).getId();
    }

    @Override
    public Rating saveAndUpdateUserDetails(RatingDto ratingDto) {
        UUID id = this.save(ratingDto);
        Rating rating = this.ratingRepository.findById(id).orElseThrow();
        User userWhoRated = this.userRepository.findById(ratingDto.getUserIdWhoRated()).orElseThrow();
        User userWhoWasRated = this.userRepository.findById(ratingDto.getUserIdWhoWasRated()).orElseThrow();
        userWhoWasRated.setAverageRatingAndNumberExchanges(rating);
        userWhoRated.updateNumberExchanges();
        this.userRepository.save(userWhoWasRated);
        this.userRepository.save(userWhoRated);

        return rating;
    }

    public List<RatingDto> getByUserId(UUID userId) {
        User user = this.userRepository.findById(userId).orElseThrow();
        return user.getRatings().stream().map(Rating::getDetails).toList();
    }
}
