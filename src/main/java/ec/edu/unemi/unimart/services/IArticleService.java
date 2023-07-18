package ec.edu.unemi.unimart.services;

import ec.edu.unemi.unimart.dtos.ArticleDto;

import java.util.List;

public interface IArticleService {
    List<ArticleDto> getAll();
}
