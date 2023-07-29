package ec.edu.unemi.unimart.repositories;

import ec.edu.unemi.unimart.dtos.ExchangeDto;
import ec.edu.unemi.unimart.models.Exchange;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.UUID;

public interface IExchangeRepository extends IRepository<Exchange, UUID> {
    @Query("SELECT e FROM Exchange e, ProposedArticle pa WHERE e.id = pa.exchange.id AND pa.article.id = :userId AND e.isAccepted = false")
    List<ExchangeDto> findByUserId(UUID userId);

    @Modifying
    @Transactional
    @Query("UPDATE ProposedArticle SET exchange.id = :exchangeId WHERE article.id = :articleId AND proposedArticle.id = :proposedArticle")
    void setExchangeId(UUID articleId, UUID proposedArticle, UUID exchangeId);
}
