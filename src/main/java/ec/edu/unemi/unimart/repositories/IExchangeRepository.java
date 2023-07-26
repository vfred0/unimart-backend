package ec.edu.unemi.unimart.repositories;

import ec.edu.unemi.unimart.dtos.ExchangeDto;
import ec.edu.unemi.unimart.models.Exchange;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.UUID;

public interface IExchangeRepository extends IRepository<Exchange, UUID> {
    @Query("SELECT e FROM Exchange e JOIN e.exchangeArticle ea WHERE ea.article.user.id = :userId")
    List<ExchangeDto> findByUserId(UUID userId);
}
