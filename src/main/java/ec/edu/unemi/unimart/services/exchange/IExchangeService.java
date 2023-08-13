package ec.edu.unemi.unimart.services.exchange;

import ec.edu.unemi.unimart.dtos.ExchangeDto;
import ec.edu.unemi.unimart.dtos.ProposalDto;
import ec.edu.unemi.unimart.dtos.RatingDto;
import jakarta.transaction.Transactional;

import java.util.List;
import java.util.UUID;

public interface IExchangeService  {
    List<ExchangeDto> findByUserId(UUID id);

    @Transactional
    UUID setExchangeMade(UUID exchangeId, RatingDto ratingDto);

    @Transactional
    void discardExchange(UUID id);

    @Transactional
    UUID acceptExchange(ProposalDto proposalDto);
}
