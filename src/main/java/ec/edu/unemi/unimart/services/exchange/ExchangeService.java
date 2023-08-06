package ec.edu.unemi.unimart.services.exchange;

import ec.edu.unemi.unimart.dtos.ExchangeDto;
import ec.edu.unemi.unimart.dtos.article.ProposedArticleDto;
import ec.edu.unemi.unimart.dtos.RatingDto;
import ec.edu.unemi.unimart.models.*;
import ec.edu.unemi.unimart.models.Rating;
import ec.edu.unemi.unimart.repositories.*;
import ec.edu.unemi.unimart.services.rating.IRatingService;
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
    private final IArticleRepository articleRepository;
    private final IRatingService ratingService;

    @Transactional
    @Override
    public UUID setExchangeMade(UUID exchangeId, RatingDto ratingDto) {
        Exchange exchange = this.exchangeRepository.findById(exchangeId).orElseThrow(() -> new RuntimeException("Exchange not found: " + exchangeId));
        Rating rating = this.ratingService.saveAndUpdateUserDetails(ratingDto);
        exchange.addRating(rating);
        exchange.updateArticlesFromMade();
        this.updateArticles(exchange);
        return rating.getId();
    }

    @Transactional
    public UUID acceptExchange(ProposedArticleDto proposedArticleDto) {
        ProposedArticle proposedArticle = this.proposedArticleRepository.findByReceiverArticleIdAndProposerArticleId(proposedArticleDto.receiverArticleId(), proposedArticleDto.proposerArticleId());
        proposedArticle.getProposerArticle().setPublished();
        Exchange exchange = new Exchange();
        exchange.setProposedArticle(proposedArticle);
        return this.exchangeRepository.save(exchange).getId();
    }

    @Override
    public void discardExchange(UUID id) {
        Exchange exchange = this.exchangeRepository.findById(id).orElseThrow(() -> new RuntimeException("Exchange not found: " + id));
        exchange.updateArticlesFromDiscard();
        this.proposedArticleRepository.deleteByProposerArticleId(exchange.getProposerArticleId());
        updateArticles(exchange);
    }

    private void updateArticles(Exchange exchange) {
        this.articleRepository.save(exchange.getProposedArticle().getReceiverArticle());
        this.articleRepository.save(exchange.getProposedArticle().getProposerArticle());
    }

    public List<ExchangeDto> findByUserId(UUID userId) {
        User user = this.userRepository.findById(userId).orElseThrow(() -> new RuntimeException("User not found: " + userId));
        return user.getExchanges();
    }
}
