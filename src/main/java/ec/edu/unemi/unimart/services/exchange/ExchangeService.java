package ec.edu.unemi.unimart.services.exchange;

import ec.edu.unemi.unimart.dtos.exchange.ExchangeDto;
import ec.edu.unemi.unimart.dtos.article.ProposedArticleDto;
import ec.edu.unemi.unimart.dtos.rating.RatingDto;
import ec.edu.unemi.unimart.mappers.Mapper;
import ec.edu.unemi.unimart.models.Exchange;
import ec.edu.unemi.unimart.models.Rating;
import ec.edu.unemi.unimart.repositories.*;
import ec.edu.unemi.unimart.services.crud.CrudService;
import ec.edu.unemi.unimart.services.rating.IRatingService;
import jakarta.transaction.Transactional;
import org.ocpsoft.prettytime.PrettyTime;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.*;

@Service
public class ExchangeService extends CrudService<Exchange, ExchangeDto, UUID> implements IExchangeService {
    private final IRatingService ratingService;
    private final IProposedArticleRepository proposedArticleRepository;

    public ExchangeService(Mapper mapper, IExchangeRepository repository, IRatingService ratingService, IProposedArticleRepository proposedArticleRepository) {
        super(mapper, repository, Exchange.class, ExchangeDto.class);
        this.ratingService = ratingService;
        this.proposedArticleRepository = proposedArticleRepository;
    }

    protected IExchangeRepository getRepository() {
        return (IExchangeRepository) super.getRepository();
    }

    @Transactional
    @Override
    public UUID save(ProposedArticleDto proposedArticleDto) {
        UUID exchangeId = this.getRepository().save(new Exchange()).getId();
        this.getRepository().updateArticlesFromAcceptExchange(exchangeId, proposedArticleDto.getProposedArticleId());
        return exchangeId;
    }

    @Override
    public List<ExchangeDto> findByUserId(UUID userId) {
        List<ExchangeDto> exchangeDtos = new ArrayList<>();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss.SSSSSS");
        this.getRepository().findByUserId(userId).forEach(exchange -> {
            ExchangeDto exchangeDto = new ExchangeDto();
            exchangeDto.setId(UUID.fromString(exchange[0].toString()));
            exchangeDto.setUserId(UUID.fromString(exchange[1].toString()));
            exchangeDto.setUserName(exchange[2].toString());
            exchangeDto.setUserPhoto(exchange[3].toString());
            exchangeDto.setArticleToExchange(exchange[4].toString());
            exchangeDto.setArticleToReceive(exchange[5].toString());
            exchangeDto.setHasBeenRated(Boolean.parseBoolean(exchange[6].toString()));
            exchangeDto.setIsDiscarded(Boolean.parseBoolean(exchange[7].toString()));
//            exchangeDto.setDate(new PrettyTime(new Locale("es")).format(LocalDateTime.parse(exchange[8].toString(), formatter)));
            exchangeDto.setDate(exchange[8].toString());
            exchangeDtos.add(exchangeDto);
        });
        return exchangeDtos;
    }

    @Override
    public UUID setExchangeMade(UUID exchangeId, RatingDto ratingDto) {
        Exchange exchange = this.getRepository().findById(exchangeId).orElseThrow(() -> new RuntimeException("Exchange not found: " + exchangeId));
        this.getRepository().updateArticlesFromMadeExchange(exchangeId, this.ratingService.save(ratingDto).getId(), ratingDto.getUserId());
        return this.getRepository().save(exchange).getId();
    }

    @Override
    public void deleteById(UUID id) {
        Exchange exchange = this.getRepository().findById(id).orElseThrow(() -> new RuntimeException("Exchange not found: " + id));
        this.proposedArticleRepository.deleteByExchangeId(id);
        this.getRepository().delete(exchange);
    }
}
