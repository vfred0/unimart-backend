package ec.edu.unemi.unimart.services.exchange;

import ec.edu.unemi.unimart.api.dtos.ExchangeDto;
import ec.edu.unemi.unimart.api.dtos.ProposalDto;
import ec.edu.unemi.unimart.api.dtos.RatingDto;
import ec.edu.unemi.unimart.data.daos.IArticleRepository;
import ec.edu.unemi.unimart.data.daos.IExchangeRepository;
import ec.edu.unemi.unimart.data.entities.Exchange;
import ec.edu.unemi.unimart.data.entities.Proposal;
import ec.edu.unemi.unimart.data.entities.Rating;
import ec.edu.unemi.unimart.services.exceptions.MessageException;
import ec.edu.unemi.unimart.services.exceptions.NotFoundException;
import ec.edu.unemi.unimart.services.proposal.IProposalService;
import ec.edu.unemi.unimart.services.rating.IRatingService;
import ec.edu.unemi.unimart.services.user.IUserService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

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
        return this.exchangeRepository.findById(exchangeId).orElseThrow(() -> new NotFoundException(MessageException.EXCHANGE_NOT_FOUND));
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
