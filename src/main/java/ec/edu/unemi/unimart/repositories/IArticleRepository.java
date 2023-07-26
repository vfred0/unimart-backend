package ec.edu.unemi.unimart.repositories;

import ec.edu.unemi.unimart.models.Article;

import java.util.UUID;

public interface IArticleRepository extends IRepository<Article, UUID> {
    @Query(value = "SELECT a.* FROM articles a " +
            "JOIN proposed_articles ap ON a.id = ap.article_id " +
            "WHERE a.user_id = :userId", nativeQuery = true)
    List<ArticleCardDto> findProposedArticlesByUserId(@Param("userId") UUID userId);

    @Query(value = "SELECT a.* FROM articles a " +
            "JOIN proposed_articles ap ON a.id = ap.article_id " +
            "WHERE ap.article_id = :id", nativeQuery = true)
    List<ArticleCardDto> findProposedArticlesByArticleId(UUID id);

    @Modifying
    @Transactional
    @Query(value = "DELETE FROM proposed_articles WHERE article_id = :articleId AND proposed_article_id = :proposedArticleId", nativeQuery = true)
    void deleteProposedArticleByArticleId(UUID articleId, UUID proposedArticleId);
}
