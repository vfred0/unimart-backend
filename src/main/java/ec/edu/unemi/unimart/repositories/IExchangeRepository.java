package ec.edu.unemi.unimart.repositories;

import ec.edu.unemi.unimart.models.Exchange;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.UUID;

public interface IExchangeRepository extends IRepository<Exchange, UUID> {

    @Query(value = "SELECT * FROM fn_get_exchange_by_user_id(CAST(:userId AS UUID))", nativeQuery = true)
    List<Object[]> findByUserId(UUID userId);

    @Query(value = "SELECT fn_update_articles_from_accept_exchange(CAST(:exchangeId AS UUID), CAST(:proposedArticleId AS UUID))", nativeQuery = true)
    void updateArticlesFromAcceptExchange(UUID exchangeId, UUID proposedArticleId);

    @Query(value = "SELECT fn_update_articles_from_made_exchange(CAST(:exchangeId AS UUID), CAST(:ratingId AS UUID), CAST(:userId AS UUID))", nativeQuery = true)
    void updateArticlesFromMadeExchange(UUID exchangeId, UUID ratingId, UUID userId);
}
