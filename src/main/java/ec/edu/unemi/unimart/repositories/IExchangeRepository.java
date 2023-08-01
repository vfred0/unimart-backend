package ec.edu.unemi.unimart.repositories;

import ec.edu.unemi.unimart.models.Exchange;
import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.UUID;

public interface IExchangeRepository extends IRepository<Exchange, UUID> {

    @Query(value = "SELECT * FROM fn_exchanges_by_user_id(CAST(:userId AS UUID))", nativeQuery = true)
    List<Object[]> findByUserId(UUID userId);

    @Query(value = "SELECT fn_update_articles_from_accept_exchange(CAST(:exchangeId AS UUID), CAST(:proposedArticleId AS UUID))", nativeQuery = true)
    void updateArticlesFromAcceptExchange(UUID exchangeId, UUID proposedArticleId);

    @Query(value = "SELECT fn_update_articles_from_made_exchange(CAST(:exchangeId AS UUID), CAST(:ratingId AS UUID), CAST(:userId AS UUID))", nativeQuery = true)
    void updateArticlesFromMadeExchange(UUID exchangeId, UUID ratingId, UUID userId);

    @Modifying
    @Transactional
    @Query(value="CALL proc_delete_exchange_by_id(cast(:id as uuid))", nativeQuery = true)
    void deleteByExchangeId(UUID id);
}
