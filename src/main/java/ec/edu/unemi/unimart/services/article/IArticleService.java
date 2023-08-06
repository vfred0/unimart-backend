package ec.edu.unemi.unimart.services.article;

import ec.edu.unemi.unimart.dtos.article.ArticleDto;
import ec.edu.unemi.unimart.models.enums.Category;
import ec.edu.unemi.unimart.models.enums.State;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface IArticleService {
    UUID save(UUID userId, ArticleDto articleDto);

    UUID update(UUID articleId, ArticleDto articleDto);

    void deleteById(UUID articleId);

    List<ArticleDto> search(String title, Category category, State state);

    List<ArticleDto> proposerArticlesByReceiverArticleId(UUID receiverArticleId);

    Optional<ArticleDto> findById(UUID articleId);

}
