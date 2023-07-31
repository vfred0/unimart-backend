package ec.edu.unemi.unimart.repositories;

import ec.edu.unemi.unimart.models.ProposedArticle;
import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

import java.util.UUID;

public interface IProposedArticleRepository extends JpaRepository<ProposedArticle, UUID> {
    @Modifying
    @Transactional
    @Query(value = "DELETE FROM ProposedArticle pa, Exchange e WHERE e.id = :id AND pa.id = e.id", nativeQuery = true)
    void deleteByExchangeId(UUID id);
}
