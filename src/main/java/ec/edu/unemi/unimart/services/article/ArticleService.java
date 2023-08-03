package ec.edu.unemi.unimart.services.article;

import ec.edu.unemi.unimart.dtos.UserDto;
import ec.edu.unemi.unimart.dtos.article.ArticleDto;
import ec.edu.unemi.unimart.mappers.Mapper;
import ec.edu.unemi.unimart.models.Article;
import ec.edu.unemi.unimart.models.User;
import ec.edu.unemi.unimart.models.enums.Category;
import ec.edu.unemi.unimart.models.enums.State;
import ec.edu.unemi.unimart.repositories.IArticleRepository;
import ec.edu.unemi.unimart.services.crud.CrudService;
import ec.edu.unemi.unimart.services.user.IUserService;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
public class ArticleService extends CrudService<Article, ArticleDto, UUID> implements IArticleService {
    private final IUserService userService;

    public ArticleService(Mapper mapper, IArticleRepository repository, IUserService userService) {
        super(mapper, repository, Article.class, ArticleDto.class);
        this.userService = userService;
    }

    @Override
    public List<ArticleDto> search(String title, Category category, State state) {
        return getRepository().findAll()
                .stream()
                .filter(article -> article.containsFilters(title, category, state))
                .filter(article -> !article.isExchanged())
                .map(article -> getMapper().toDto(article, ArticleDto.class)).toList();
    }

    @Override
    public List<ArticleDto> proposerArticlesByReceiverArticleId(UUID receiverArticleId) {
        Article receiverArticle = this.getRepository().findById(receiverArticleId).orElseThrow(() -> new RuntimeException("Artículo no encontrado"));
        return this.getMapper().toDtos(receiverArticle.getProposerArticles(), ArticleDto.class);
    }

    @Override
    public Optional<ArticleDto> findById(UUID articleId) {
        Article article = this.getRepository().findById(articleId).orElseThrow(() -> new RuntimeException("Artículo no encontrado"));
        ArticleDto articleDto = this.getMapper().toDto(article, ArticleDto.class);
        articleDto.setAcceptProposals(article.isAcceptProposals());
        articleDto.setReceiverUserIdForArticle(article.getReceiverUserIdForArticle());
        articleDto.setProposersUserIdsForArticle(article.getProposersUserIdsForArticle());
        return Optional.of(articleDto);
    }

    @Override
    public ArticleDto save(UUID userId, ArticleDto articleDto) {
        UserDto user = userService.findById(userId).orElseThrow(() -> new RuntimeException("Usuario no encontrado"));
        Article article = this.getMapper().toModel(articleDto, Article.class);
        article.setUser(this.getMapper().toModel(user, User.class));
        return this.getMapper().toDto(this.getRepository().save(article), ArticleDto.class);
    }

    @Override
    public ArticleDto update(UUID articleId, ArticleDto articleDto) {
        Article article = this.getRepository().findById(articleId).orElseThrow(() -> new RuntimeException("Artículo no encontrado"));
        Article articleUpdated = this.getMapper().toModel(articleDto, Article.class);
        articleUpdated.setId(articleId);
        articleUpdated.setUser(article.getUser());
        articleUpdated.setNumbersProposals(article.getNumbersProposals());
        return this.getMapper().toDto(this.getRepository().save(articleUpdated), ArticleDto.class);
    }

    @Override
    public List<ArticleDto> findByUserId(UUID id) {
        return this.getRepository().findByUserId(id).stream().map(article -> this.getMapper().toDto(article, ArticleDto.class)).toList();
    }

    @Override
    protected IArticleRepository getRepository() {
        return (IArticleRepository) super.getRepository();
    }
}