package ec.edu.unemi.unimart.repositories;

import ec.edu.unemi.unimart.models.Article;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.UUID;

public interface IArticleRepository extends IRepository<Article, UUID> {
    @Modifying
    @Transactional
    @Query(value = "DELETE FROM proposed_articles WHERE proposed_article_id = :articleId", nativeQuery = true)
    void deleteProposedArticleById(UUID articleId);

    @Query(value = "SELECT fn_user_has_made_proposed(CAST(:userId AS UUID), CAST(:articleId AS UUID))", nativeQuery = true)
    Boolean userHasMadeProposed(UUID userId, UUID articleId);

    @Query(value = "SELECT fn_update_articles_from_deleted(CAST(:articleId AS UUID), CAST(:onlyUpdateNumberProposals AS BOOLEAN))", nativeQuery = true)
    void updateTypeArticleFromDeleted(UUID articleId, boolean onlyUpdateNumberProposals);

    @Query(value = "SELECT fn_exists_proposed_article(CAST(:proposedArticleId AS UUID))", nativeQuery = true)
    boolean existsProposedArticle(UUID proposedArticleId);

    @Query(value = "SELECT a.* FROM articles a, proposed_articles pa WHERE pa.article_id = a.id AND pa.proposed_article_id = :proposedArticleId", nativeQuery = true)
    Article getArticleByProposedArticleId(UUID proposedArticleId);

    @Modifying
    @Transactional
    @Query(value = "DELETE FROM proposed_articles WHERE article_id = :articleId OR proposed_article_id = :articleId", nativeQuery = true)
    void deleteAllMatchesByArticleId(UUID articleId);

    @Query(value = "SELECT * FROM fn_exchange_details_by_article_id(CAST(:articleId AS UUID))", nativeQuery = true)
    List<Object[]> findExchangeDetailsByArticleId(UUID articleId);
}