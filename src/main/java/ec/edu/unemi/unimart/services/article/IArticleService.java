package ec.edu.unemi.unimart.services.article;

import ec.edu.unemi.unimart.dtos.ArticleDto;

import java.util.List;
import java.util.UUID;

public interface IArticleService {
    List<ArticleDto> getAll();

    UUID save(ArticleDto articleDto);
}
