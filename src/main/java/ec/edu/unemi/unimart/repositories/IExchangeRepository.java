package ec.edu.unemi.unimart.repositories;

import ec.edu.unemi.unimart.dtos.ExchangeDto;
import ec.edu.unemi.unimart.models.Exchange;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.UUID;

public interface IExchangeRepository extends IRepository<Exchange, UUID> {

    @Query("SELECT e FROM Article a, ProposedArticle pa, Exchange e WHERE a.user.id = :userId AND (a.id = pa.article.id OR a.id = pa.proposedArticle.id) AND pa.exchange.id IS NOT NULL")
    List<Exchange> findByUserId(UUID userId);

    @Query(value = "SELECT fn_update_articles_from_accept_exchange(CAST(:exchangeId AS UUID), CAST(:articleId AS UUID), CAST(:proposedArticleId AS UUID))", nativeQuery = true)
    void updateArticlesFromAcceptExchange(UUID exchangeId, UUID articleId, UUID proposedArticleId);

}
