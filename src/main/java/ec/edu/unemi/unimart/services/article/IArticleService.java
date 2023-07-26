package ec.edu.unemi.unimart.services.article;

import ec.edu.unemi.unimart.dtos.article.ArticleDto;
import ec.edu.unemi.unimart.dtos.article.ArticleCardDto;
import ec.edu.unemi.unimart.dtos.article.SuggestArticleDto;
import ec.edu.unemi.unimart.services.crud.ICrudService;
import ec.edu.unemi.unimart.models.enums.Category;
import ec.edu.unemi.unimart.models.enums.State;

import java.util.List;
import java.util.UUID;

public interface IArticleService extends ICrudService<ArticleDto, UUID> {
    List<ArticleDto> search(String title, Category category, State state);

    List<ArticleCardDto> findByUserId(UUID id);

    UUID addProposal(SuggestArticleDto suggestArticleDto);

    List<ArticleCardDto> proposedArticles(UUID id);
}
