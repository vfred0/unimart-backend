package ec.edu.unemi.unimart.repositories;

import ec.edu.unemi.unimart.dtos.article.ArticleCardDto;
import ec.edu.unemi.unimart.models.Article;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.UUID;

public interface IArticleRepository extends IRepository<Article, UUID> {
    @Query(value = "SELECT a.* FROM articles a " +
            "JOIN proposed_articles ap ON a.id = ap.article_id " +
            "WHERE ap.article_id = :id", nativeQuery = true)
    List<ArticleCardDto> findProposedArticlesByArticleId(UUID id);

    @Modifying
    @Transactional
    @Query(value = "DELETE FROM proposed_articles WHERE article_id = :articleId AND proposed_article_id = :proposedArticleId", nativeQuery = true)
    void deleteProposedArticleByArticleId(UUID articleId, UUID proposedArticleId);
}