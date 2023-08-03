package ec.edu.unemi.unimart.repositories;

import ec.edu.unemi.unimart.models.Article;

import java.util.List;
import java.util.UUID;

public interface IArticleRepository extends IRepository<Article, UUID> {
    List<Article> findByUserId(UUID id);
}