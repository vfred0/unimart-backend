package ec.edu.unemi.unimart.services.article;

import ec.edu.unemi.unimart.dtos.UserDto;
import ec.edu.unemi.unimart.dtos.article.ArticleCardDto;
import ec.edu.unemi.unimart.dtos.article.ArticleDto;
import ec.edu.unemi.unimart.mappers.IArticleMapper;
import ec.edu.unemi.unimart.mappers.Mapper;
import ec.edu.unemi.unimart.models.Article;
import ec.edu.unemi.unimart.models.enums.Category;
import ec.edu.unemi.unimart.models.enums.State;
import ec.edu.unemi.unimart.models.enums.TypeArticle;
import ec.edu.unemi.unimart.repositories.IArticleRepository;
import ec.edu.unemi.unimart.services.crud.CrudService;
import ec.edu.unemi.unimart.services.user.IUserService;
import org.springframework.stereotype.Service;

import java.util.*;

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
        return getRepository().findAll().stream().filter(article -> article.getTitle().toLowerCase().contains(title.toLowerCase())).filter(article -> article.getCategory().equals(category)).filter(article -> article.getState().equals(state)).filter(article -> !TypeArticle.isExchanged(article.getTypeArticle())).map(article -> getMapper().toDto(article, ArticleDto.class)).toList();
    }

    @Override
    public ArticleDto save(ArticleDto articleDto) {
        Optional<UserDto> userDto = this.userService.findById(articleDto.getUserId());
        userDto.orElseThrow(() -> new RuntimeException("Usuario no encontrado"));
        articleDto.setUser(userDto.get());
        this.getRepository().findAll().stream().filter(searchArticle -> searchArticle.getId().equals(articleDto.getId())).findFirst().ifPresent(article -> articleDto.setNumbersProposals(article.getNumbersProposals()));
        return this.articleMapper.toDto(this.getRepository().save(this.articleMapper.toModel(articleDto)));
    }

    @Override
    public ArticleDto update(UUID uuid, ArticleDto articleDto) {
        return this.save(articleDto);
    }

    @Override
    public List<ArticleDto> findByUserId(UUID id) {
        return getRepository().findAll().stream()
                .filter(article -> article.getUser().getId().equals(id))
                .map(article -> getMapper().toDto(article, ArticleDto.class))
                .peek(articleDto ->
                        {
                            List<Object[]> exchangeDetails = this.getRepository().findExchangeDetailsByArticleId(articleDto.getId());
                            articleDto.setAcceptProposals(Boolean.parseBoolean(exchangeDetails.get(0)[0].toString()));
                        }
                ).toList();
    }

    @Override
    protected IArticleRepository getRepository() {
        return (IArticleRepository) super.getRepository();
    }

    @Override
    public void deleteById(UUID articleId) {
        this.getRepository().updateTypeArticleFromDeleted(articleId, false);
        this.getRepository().deleteAllMatchesByArticleId(articleId);
        this.getRepository().deleteById(articleId);
    }

    @Override
    public Optional<ArticleDto> findById(UUID id) {
        Article article = this.getRepository().findById(id).orElseThrow(() -> new RuntimeException("Artículo no encontrado"));
        ArticleDto articleDto = this.getMapper().toDto(article, ArticleDto.class);
        return Optional.of(addExchangeDetails(articleDto));
    }

    private ArticleDto addExchangeDetails(ArticleDto articleDto) {
        List<Object[]> exchangeDetails = this.getRepository().findExchangeDetailsByArticleId(articleDto.getId());
        exchangeDetails.forEach(exchangeDetail -> {
            articleDto.setAcceptProposals(Boolean.parseBoolean(exchangeDetail[0].toString()));
            if (Objects.nonNull(exchangeDetail[1])) {
                articleDto.setReceiverUserIdForArticle(UUID.fromString(exchangeDetail[1].toString()));
            }
            if (Objects.nonNull(exchangeDetail[2])) {
                articleDto.setProposersUserIdsForArticle(Arrays.asList((UUID[]) exchangeDetail[2]));
            }
        });

        return articleDto;

    }
}