package ec.edu.unemi.unimart.repositories;

import ec.edu.unemi.unimart.dtos.ArticleDto;

import java.util.List;

public interface IArticleRepository {
    List<ArticleDto> getAll();
}
