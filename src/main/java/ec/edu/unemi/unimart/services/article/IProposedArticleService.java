package ec.edu.unemi.unimart.services.article;

import ec.edu.unemi.unimart.dtos.article.ArticleCardDto;
import ec.edu.unemi.unimart.dtos.article.ProposedArticleDto;

import java.util.List;
import java.util.UUID;

public interface IProposedArticleService {
    UUID save(ProposedArticleDto proposedArticleDto);

    List<ArticleCardDto> proposedArticlesByArticleId(UUID id);

    List<ArticleCardDto> proposedArticlesByUserId(UUID id);

    void deleteById(UUID id);

    Boolean userHasMadeProposed(UUID articleId, UUID userId);

}
