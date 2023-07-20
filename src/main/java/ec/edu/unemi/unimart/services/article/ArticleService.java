package ec.edu.unemi.unimart.services.article;

import ec.edu.unemi.unimart.dtos.ArticleDto;
import ec.edu.unemi.unimart.models.Article;
import ec.edu.unemi.unimart.repositories.IArticleRepository;
import ec.edu.unemi.unimart.services.crud.CrudService;
import ec.edu.unemi.unimart.utils.Mapper;
import org.springframework.stereotype.Service;

import java.util.UUID;


@Service
public class ArticleService extends CrudService<Article, ArticleDto, UUID> implements IArticleService {

    public ArticleService(Mapper mapper, IArticleRepository repository) {
        super(mapper, repository, Article.class, ArticleDto.class);
    }
}