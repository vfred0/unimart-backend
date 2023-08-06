package ec.edu.unemi.unimart.services.exchange;

import ec.edu.unemi.unimart.dtos.ExchangeDto;
import ec.edu.unemi.unimart.dtos.article.ProposedArticleDto;
import ec.edu.unemi.unimart.dtos.RatingDto;

import java.util.List;
import java.util.UUID;

public interface IExchangeService  {
    List<ExchangeDto> findByUserId(UUID id);

    UUID setExchangeMade(UUID exchangeId, RatingDto ratingDto);

    void discardExchange(UUID id);

    UUID acceptExchange(ProposedArticleDto proposedArticleDto);
}
