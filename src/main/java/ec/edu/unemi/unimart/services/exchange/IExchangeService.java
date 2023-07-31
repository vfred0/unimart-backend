package ec.edu.unemi.unimart.services.exchange;

import ec.edu.unemi.unimart.dtos.ExchangeDto;
import ec.edu.unemi.unimart.dtos.article.ProposedArticleDto;
import ec.edu.unemi.unimart.dtos.rating.RatingDto;
import ec.edu.unemi.unimart.services.crud.ICrudService;

import java.util.List;
import java.util.UUID;

public interface IExchangeService extends ICrudService<ExchangeDto, UUID> {
    UUID save(ProposedArticleDto proposedArticleDto);

    List<ExchangeDto> findByUserId(UUID id);

    UUID setExchangeMade(UUID exchangeId, RatingDto ratingDto);
}
