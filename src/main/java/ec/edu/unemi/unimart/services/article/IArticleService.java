package ec.edu.unemi.unimart.services.article;

import ec.edu.unemi.unimart.api.dtos.ArticleDto;
import ec.edu.unemi.unimart.data.enums.Category;
import ec.edu.unemi.unimart.data.enums.State;
import jakarta.transaction.Transactional;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface IArticleService {
    @Transactional
    UUID save(UUID userId, ArticleDto articleDto);

    @Transactional
    UUID update(UUID articleId, ArticleDto articleDto);

    @Transactional
    void deleteById(UUID articleId);

    List<ArticleDto> search(String title, Category category, State state);

    List<ArticleDto> proposerArticlesByReceiverArticleId(UUID receiverArticleId);

    Optional<ArticleDto> findById(UUID articleId);

}
