package ec.edu.unemi.unimart.services.article;

import ec.edu.unemi.unimart.dtos.ArticleDto;
import ec.edu.unemi.unimart.services.crud.ICrudService;
import ec.edu.unemi.unimart.utils.Category;
import ec.edu.unemi.unimart.utils.State;

import java.util.List;
import java.util.UUID;

public interface IArticleService extends ICrudService<ArticleDto, UUID> {
    List<ArticleDto> search(String title, Category category, State state);
}
