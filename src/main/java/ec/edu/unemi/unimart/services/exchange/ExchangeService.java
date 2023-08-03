package ec.edu.unemi.unimart.services.exchange;

import ec.edu.unemi.unimart.dtos.ExchangeDto;
import ec.edu.unemi.unimart.dtos.article.ProposedArticleDto;
import ec.edu.unemi.unimart.dtos.RatingDto;
import ec.edu.unemi.unimart.models.Exchange;
import ec.edu.unemi.unimart.models.ProposedArticle;
import ec.edu.unemi.unimart.models.Rating;
import ec.edu.unemi.unimart.models.User;
import ec.edu.unemi.unimart.models.enums.TypeArticle;
import ec.edu.unemi.unimart.repositories.*;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
@RequiredArgsConstructor
public class ExchangeService implements IExchangeService {

    private final IExchangeRepository exchangeRepository;
    private final IUserRepository userRepository;
    private final IProposedArticleRepository proposedArticleRepository;
    private final IRatingRepository ratingRepository;

    @Transactional
    @Override
    public UUID setExchangeMade(UUID exchangeId, RatingDto ratingDto) {
        Exchange exchange = this.exchangeRepository.findById(exchangeId).orElseThrow(() -> new RuntimeException("Exchange not found: " + exchangeId));
        User userWhoRated = this.userRepository.findById(ratingDto.getUserIdWhoRated()).orElseThrow(() -> new RuntimeException("User not found: " + ratingDto.getUserIdWhoRated()));
        User userWhoWasRated = this.userRepository.findById(ratingDto.getUserIdWhoWasRated()).orElseThrow(() -> new RuntimeException("User not found: " + ratingDto.getUserIdWhoWasRated()));

        Rating rating = this.ratingRepository.save(Rating.builder()
                .userIdWhoRated(userWhoRated)
                .userIdWhoWasRated(userWhoWasRated)
                .score(ratingDto.getScore())
                .comment(ratingDto.getComment())
                .build());
        exchange.addRating(rating);
        return rating.getId();
    }

    @Transactional
    public UUID acceptExchange(ProposedArticleDto proposedArticleDto) {
        ProposedArticle proposedArticle = this.proposedArticleRepository
                .findByReceiverArticleIdAndProposerArticleId(proposedArticleDto.receiverArticle(), proposedArticleDto.proposerArticle());
        proposedArticle.getReceiverArticle().decrementNumberProposals();
        proposedArticle.getProposerArticle().setTypeArticle(TypeArticle.PUBLISHED);
        Exchange exchange = new Exchange();
        exchange.setProposedArticle(proposedArticle);
        return this.exchangeRepository.save(exchange).getId();
    }


    @Override
    public void deleteById(UUID id) {
        this.exchangeRepository.deleteById(id);
    }

    public List<ExchangeDto> findByUserId(UUID userId) {
        User user = this.userRepository.findById(userId).orElseThrow(() -> new RuntimeException("User not found: " + userId));
        return user.getArticles().stream()
                .filter(article -> article.getExchange() != null)
                .map(article -> article.getExchange().getDetails(user))
                .toList();
    }
}
