package ec.edu.unemi.unimart.repositories.article;

import ec.edu.unemi.unimart.dtos.ArticleDto;

import java.util.List;
import java.util.UUID;

public interface IArticleRepository {
    List<ArticleDto> getAll();

    UUID save(ArticleDto articleDto);
}
