package ec.edu.unemi.unimart.services.article;

import ec.edu.unemi.unimart.dtos.UserDto;
import ec.edu.unemi.unimart.dtos.article.ArticleDto;
import ec.edu.unemi.unimart.dtos.article.ArticleCardDto;
import ec.edu.unemi.unimart.dtos.article.SuggestArticleDto;
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
import java.util.Set;
import java.util.UUID;
import java.util.logging.Logger;

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
        Logger.getLogger("ArticleService").info("ArticleDto: " + articleDto);
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
    public UUID addProposal(SuggestArticleDto suggestArticleDto) {
        Article article = this.getRepository().findById(suggestArticleDto.getArticleId()).orElseThrow(() -> new RuntimeException("Articulo no encontrado"));
        Article suggestArticle = this.getRepository().findById(suggestArticleDto.getSuggestArticleId()).orElseThrow(() -> new RuntimeException("Propuesta no encontrada"));
        article.setSuggestArticle(suggestArticle.getId());
        return this.getRepository().save(article).getId();
    }

    @Override
    public List<ArticleCardDto> proposedArticlesByArticleId(UUID articleId) {
        Article article = this.getRepository().findById(articleId).orElseThrow(() -> new RuntimeException("Articulo no encontrado"));
        Logger.getLogger("ArticleService").info("Article: " + article);
        return this.getRepository().findProposedArticlesByArticleId(article.getUser().getId());
    }

    @Override
    protected IArticleRepository getRepository() {
        return (IArticleRepository) super.getRepository();
    }

    @Override
    public List<ArticleCardDto> proposedArticlesByUserId(UUID userId) {
        return getRepository().findProposedArticlesByUserId(userId);
    }

    @Override
    public void deleteProposedArticleById(UUID id) {

    }

    @Override
    public void deleteProposedArticleByArticleId(UUID articleId, UUID proposedArticleId) {
        Article article = this.getRepository().findById(articleId).orElseThrow(() -> new RuntimeException("Articulo no encontrado"));
        this.getRepository().deleteProposedArticleByArticleId(articleId, proposedArticleId);
        article.removeProposedArticle(proposedArticleId);
        this.getRepository().save(article);
    }
}