package ec.edu.unemi.unimart.services.exchange;

import ec.edu.unemi.unimart.dtos.ExchangeDto;
import ec.edu.unemi.unimart.dtos.ProposalDto;
import ec.edu.unemi.unimart.dtos.RatingDto;
import ec.edu.unemi.unimart.exceptions.MessageException;
import ec.edu.unemi.unimart.exceptions.NotFoundException;
import ec.edu.unemi.unimart.models.*;
import ec.edu.unemi.unimart.models.Rating;
import ec.edu.unemi.unimart.repositories.*;
import ec.edu.unemi.unimart.services.proposal.IProposalService;
import ec.edu.unemi.unimart.services.rating.IRatingService;
import ec.edu.unemi.unimart.services.user.IUserService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
@RequiredArgsConstructor
public class ExchangeService implements IExchangeService {

    private final IUserService userService;
    private final IRatingService ratingService;
    private final IProposalService proposalService;
    private final IExchangeRepository exchangeRepository;
    private final IArticleRepository articleRepository;

    public UUID setExchangeMade(UUID exchangeId, RatingDto ratingDto) {
        Exchange exchange = getExchange(exchangeId);
        Rating rating = this.ratingService.saveAndUpdateUserDetails(ratingDto);
        exchange.addRating(rating);
        exchange.updateArticlesFromMade();
        this.updateArticles(exchange);
        this.proposalService.deleteOthersProposals(exchange.getProposal());
        return rating.getId();
    }

    private Exchange getExchange(UUID exchangeId) {
        return this.exchangeRepository.findById(exchangeId).orElseThrow(() -> NotFoundException.throwBecauseOf(MessageException.EXCHANGE_NOT_FOUND));
    }

    public UUID acceptExchange(ProposalDto proposalDto) {
        Proposal proposal = this.proposalService.findByProposalDto(proposalDto);
//        proposal.getProposerArticle().setPublished();
        Exchange exchange = new Exchange();
        exchange.setProposal(proposal);
        return this.exchangeRepository.save(exchange).getId();
    }

    public void discardExchange(UUID id) {
        Exchange exchange = getExchange(id);
        exchange.updateArticlesFromDiscard();
        this.proposalService.deleteByProposerArticleId(exchange.getProposerArticleId());
        updateArticles(exchange);
    }

    private void updateArticles(Exchange exchange) {
        this.articleRepository.save(exchange.getProposal().getReceiverArticle());
        this.articleRepository.save(exchange.getProposal().getProposerArticle());
    }

    public List<ExchangeDto> findByUserId(UUID userId) {
        return this.userService.getUserById(userId).getExchanges();
    }
}
