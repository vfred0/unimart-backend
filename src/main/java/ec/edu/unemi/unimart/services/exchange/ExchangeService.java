package ec.edu.unemi.unimart.services.exchange;

import ec.edu.unemi.unimart.dtos.ExchangeDto;
import ec.edu.unemi.unimart.dtos.article.ProposedArticleDto;
import ec.edu.unemi.unimart.dtos.RatingDto;
import ec.edu.unemi.unimart.models.*;
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
    private final IArticleRepository articleRepository;

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

        Article receiverArticle = exchange.getProposedArticle().getReceiverArticle();
        Article proposerArticle = exchange.getProposedArticle().getProposerArticle();

        receiverArticle.updateArticlesFromDeleteOrExchanged();
        proposerArticle.updateArticlesFromDeleteOrExchanged();

        receiverArticle.setTypeArticle(TypeArticle.EXCHANGED);
        proposerArticle.setTypeArticle(TypeArticle.EXCHANGED);

        this.articleRepository.save(receiverArticle);
        this.articleRepository.save(proposerArticle);

        userWhoWasRated.setAverageRatingAndNumberExchanges(rating);
        this.userRepository.save(userWhoWasRated);

        userWhoRated.updateNumberExchanges();
        this.userRepository.save(userWhoRated);

        return rating.getId();
    }

    @Transactional
    public UUID acceptExchange(ProposedArticleDto proposedArticleDto) {
        ProposedArticle proposedArticle = this.proposedArticleRepository
                .findByReceiverArticleIdAndProposerArticleId(proposedArticleDto.receiverArticleId(), proposedArticleDto.proposerArticleId());
        proposedArticle.getProposerArticle().setPublished();
        Exchange exchange = new Exchange();
        exchange.setProposedArticle(proposedArticle);
        return this.exchangeRepository.save(exchange).getId();
    }


    @Override
    public void discardExchange(UUID id) {
        Exchange exchange = this.exchangeRepository.findById(id).orElseThrow(() -> new RuntimeException("Exchange not found: " + id));
        Article proposerArticle = exchange.getProposedArticle().getProposerArticle();
        Article receiverArticle = exchange.getProposedArticle().getReceiverArticle();

        receiverArticle.removeProposer(proposerArticle);

        proposerArticle.setPublished();

        this.proposedArticleRepository.deleteByProposerArticleId(proposerArticle.getId());

        this.articleRepository.save(receiverArticle);
        this.articleRepository.save(proposerArticle);
    }

    public List<ExchangeDto> findByUserId(UUID userId) {
        User user = this.userRepository.findById(userId).orElseThrow(() -> new RuntimeException("User not found: " + userId));
        return user.getArticles().stream()
                .map(Article::getExchanges)
                .filter(Objects::nonNull)
                .flatMap(Collection::stream)
                .map(exchange -> exchange.getExchangeDetails(user))
                .toList();
    }
}
