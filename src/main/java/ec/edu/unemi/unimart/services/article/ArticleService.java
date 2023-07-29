package ec.edu.unemi.unimart.services.article;

import ec.edu.unemi.unimart.dtos.UserDto;
import ec.edu.unemi.unimart.dtos.article.ArticleCardDto;
import ec.edu.unemi.unimart.dtos.article.ArticleDto;
import ec.edu.unemi.unimart.mappers.IArticleMapper;
import ec.edu.unemi.unimart.mappers.Mapper;
import ec.edu.unemi.unimart.models.Article;
import ec.edu.unemi.unimart.models.enums.Category;
import ec.edu.unemi.unimart.models.enums.State;
import ec.edu.unemi.unimart.repositories.IArticleRepository;
import ec.edu.unemi.unimart.services.crud.CrudService;
import ec.edu.unemi.unimart.services.user.IUserService;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
public class ArticleService extends CrudService<Article, ArticleDto, UUID> implements IArticleService {
    private final IArticleMapper articleMapper;
    private final IUserService userService;

    public ArticleService(Mapper mapper, IArticleRepository repository, IArticleMapper articleMapper, IUserService userService) {
        super(mapper, repository, Article.class, ArticleDto.class);
        this.articleMapper = articleMapper;
        this.userService = userService;
    }

    @Override
    public List<ArticleDto> search(String title, Category category, State state) {
        return getRepository().findAll().stream()
                .filter(article -> article.getTitle().toLowerCase().contains(title.toLowerCase()))
                .filter(article -> article.getCategory().equals(category))
                .filter(article -> article.getState().equals(state))
                .map(article -> getMapper().toDto(article, ArticleDto.class)).toList();
    }

    @Override
    public ArticleDto save(ArticleDto articleDto) {
        Optional<UserDto> userDto = this.userService.findById(articleDto.getUserId());
        userDto.orElseThrow(() -> new RuntimeException("Usuario no encontrado"));
        articleDto.setUser(userDto.get());
        return this.articleMapper.toDto(this.getRepository().save(this.articleMapper.toModel(articleDto)));
    }

    @Override
    public ArticleDto update(UUID uuid, ArticleDto articleDto) {
        return this.save(articleDto);
    }

    @Override
    public List<ArticleCardDto> findByUserId(UUID id) {
        return getRepository().findAll().stream()
                .filter(article -> article.getUser().getId().equals(id))
                .map(article -> getMapper().toDto(article, ArticleCardDto.class)).toList();
    }

    @Override
    protected IArticleRepository getRepository() {
        return (IArticleRepository) super.getRepository();
    }

    @Override
    public void delete(UUID articleId) {
        this.getRepository().updateTypeArticleFromDeleted(articleId, false);
        this.getRepository().deleteAllMatchesByArticleId(articleId);
        this.getRepository().deleteById(articleId);
    }
}