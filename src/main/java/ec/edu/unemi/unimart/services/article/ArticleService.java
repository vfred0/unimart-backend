package ec.edu.unemi.unimart.services.article;

import ec.edu.unemi.unimart.dtos.ArticleDto;
import ec.edu.unemi.unimart.models.Article;
import ec.edu.unemi.unimart.models.User;
import ec.edu.unemi.unimart.repositories.IArticleRepository;
import ec.edu.unemi.unimart.services.crud.CrudService;
import ec.edu.unemi.unimart.utils.*;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
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
                .map(this::getArticleDto).toList();
    }

    @Override
    public ArticleDto save(ArticleDto dto) {
        User user = User.builder().id(dto.getUserId()).build();
        Article article = Article.builder()
                .user(user)
                .title(dto.getTitle())
                .description(dto.getDescription())
                .images(dto.getImages())
                .category(Category.byName(dto.getCategory()))
                .state(State.byName(dto.getState()))
                .gender(Gender.byName(dto.getGender()))
                .typeArticle(TypeArticle.byName(dto.getTypeArticle()))
                .date(dto.getDate()).numbersProposals(0).build();
        return getMapper().toDto(getRepository().save(article), ArticleDto.class);
    }

    @Override
    public List<ArticleDto> getAll() {
        return getRepository().findAll().stream().map(this::getArticleDto).toList();
    }

    private ArticleDto getArticleDto(Article article) {
        ArticleDto dto = getMapper().toDto(article, ArticleDto.class);
        dto.setUserId(null);
        return dto;
    }


    @Override
    public ArticleDto update(UUID id, ArticleDto articleDto) {
        Optional<Article> articleEntity = getRepository().findById(id);
        articleEntity.orElseThrow(() -> new RuntimeException("No se encontró el artículo"));
        Article article = articleEntity.get();
        article.setId(articleDto.getId());
        article.setUser(article.getUser());
        article.setTitle(articleDto.getTitle());
        article.setDescription(articleDto.getDescription());
        article.setImages(articleDto.getImages());
        article.setCategory(Category.byName(articleDto.getCategory()));
        article.setState(State.byName(articleDto.getState()));
        if (articleDto.getGender() != null) {
            article.setGender(Gender.byName(articleDto.getGender()));
        }
        article.setTypeArticle(TypeArticle.byName(articleDto.getTypeArticle()));
        article.setNumbersProposals(articleDto.getNumbersProposals());
        article.setDate(articleDto.getDate());
        return getMapper().toDto(getRepository().save(article), ArticleDto.class);
    }


    @Override
    public Optional<ArticleDto> findById(UUID id) {
        return getRepository().findById(id).map(this::getArticleDto);
    }
}