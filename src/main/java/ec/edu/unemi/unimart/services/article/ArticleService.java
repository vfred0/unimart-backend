package ec.edu.unemi.unimart.services.article;

import ec.edu.unemi.unimart.dtos.ArticleDto;
import ec.edu.unemi.unimart.models.Article;
import ec.edu.unemi.unimart.repositories.IArticleRepository;
import ec.edu.unemi.unimart.services.crud.CrudService;
import ec.edu.unemi.unimart.utils.Category;
import ec.edu.unemi.unimart.utils.Mapper;
import ec.edu.unemi.unimart.utils.State;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
public class ArticleService extends CrudService<Article, ArticleDto, UUID> implements IArticleService {

    public ArticleService(Mapper mapper, IArticleRepository repository) {
        super(mapper, repository, Article.class, ArticleDto.class);
    }

    @Override
    public List<ArticleDto> search(String title, Category category, State state) {
        return getRepository().findAll().stream()
                .filter(article -> article.getTitle().toLowerCase().contains(title.toLowerCase()))
                .filter(article -> article.getCategory().equals(category))
                .filter(article -> article.getState().equals(state))
                .map(article -> getMapper().toDto(article, ArticleDto.class)).toList();
    }
}