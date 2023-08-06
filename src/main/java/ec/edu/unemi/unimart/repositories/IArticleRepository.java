package ec.edu.unemi.unimart.repositories;

import ec.edu.unemi.unimart.models.Article;
import ec.edu.unemi.unimart.models.enums.Category;
import ec.edu.unemi.unimart.models.enums.State;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.UUID;

public interface IArticleRepository extends IRepository<Article, UUID> {
    @Query("SELECT a FROM Article a WHERE a.title LIKE %?1% AND a.category = ?2 AND a.state = ?3 AND a.typeArticle != 'EXCHANGED'")
    List<Article> findByTitleAndCategoryAndState(String title, Category category, State state);
}