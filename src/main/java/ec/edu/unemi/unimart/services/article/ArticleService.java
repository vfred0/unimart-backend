package ec.edu.unemi.unimart.services.article;

import ec.edu.unemi.unimart.dtos.UserDto;
import ec.edu.unemi.unimart.dtos.article.ArticleDto;
import ec.edu.unemi.unimart.mappers.IArticleMapper;
import ec.edu.unemi.unimart.mappers.Mapper;
import ec.edu.unemi.unimart.models.Article;
import ec.edu.unemi.unimart.models.User;
import ec.edu.unemi.unimart.models.enums.Category;
import ec.edu.unemi.unimart.models.enums.State;
import ec.edu.unemi.unimart.models.enums.TypeArticle;
import ec.edu.unemi.unimart.repositories.IArticleRepository;
import ec.edu.unemi.unimart.services.crud.CrudService;
import ec.edu.unemi.unimart.services.user.IUserService;
import org.springframework.stereotype.Service;

import java.util.*;
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

    //
//    @Override
//    public List<ArticleDto> search(String title, Category category, State state) {
//        return getRepository().findAll().stream().filter(article -> article.getTitle().toLowerCase().contains(title.toLowerCase())).filter(article -> article.getCategory().equals(category)).filter(article -> article.getState().equals(state)).filter(article -> !TypeArticle.isExchanged(article.getTypeArticle())).map(article -> getMapper().toDto(article, ArticleDto.class)).toList();
//    }
//
//    @Override
//    public Optional<ArticleDto> findById(UUID articleId) {
//        Article article = this.getRepository().findById(articleId).orElseThrow(() -> new RuntimeException("Artículo no encontrado"));
//        ArticleDto articleDto = this.getMapper().toDto(article, ArticleDto.class);
//        return Optional.of(articleDto);
//
//    }

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

    //
    @Override
    protected IArticleRepository getRepository() {
        return (IArticleRepository) super.getRepository();
    }
//
//    @Override
//    public void deleteById(UUID articleId) {
//        this.getRepository().updateTypeArticleFromDeleted(articleId, false);
//        this.getRepository().deleteById(articleId);
//    }
//

//
//    private ArticleDto addExchangeDetails(ArticleDto articleDto) {
//        List<Object[]> exchangeDetails = this.getRepository().findExchangeDetailsByArticleId(articleDto.getId());
//        exchangeDetails.forEach(exchangeDetail -> {
//            articleDto.setAcceptProposals(Boolean.parseBoolean(exchangeDetail[0].toString()));
//            if (Objects.nonNull(exchangeDetail[1])) {
//                articleDto.setReceiverUserIdForArticle(UUID.fromString(exchangeDetail[1].toString()));
//            }
//            if (Objects.nonNull(exchangeDetail[2])) {
//                articleDto.setProposersUserIdsForArticle(Arrays.asList((UUID[]) exchangeDetail[2]));
//            }
//        });
//
//        return articleDto;
//
//    }
}