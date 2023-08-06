package ec.edu.unemi.unimart.services.article;

import ec.edu.unemi.unimart.dtos.UserDto;
import ec.edu.unemi.unimart.dtos.article.ArticleDto;
import ec.edu.unemi.unimart.mappers.Mapper;
import ec.edu.unemi.unimart.models.Article;
import ec.edu.unemi.unimart.models.User;
import ec.edu.unemi.unimart.models.enums.Category;
import ec.edu.unemi.unimart.models.enums.State;
import ec.edu.unemi.unimart.repositories.IArticleRepository;
import ec.edu.unemi.unimart.services.user.IUserService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
@RequiredArgsConstructor
public class ArticleService implements IArticleService {

    private final IUserService userService;
    private final IArticleRepository articleRepository;
    private final Mapper mapper;

    public List<ArticleDto> search(String title, Category category, State state) {
        return this.mapper.toDtos(this.articleRepository.findByTitleAndCategoryAndState(title, category, state), ArticleDto.class);
    }

    public List<ArticleDto> proposerArticlesByReceiverArticleId(UUID receiverArticleId) {
        Article receiverArticle = this.articleRepository.findById(receiverArticleId).orElseThrow(() -> new RuntimeException("Artículo no encontrado"));
        return this.mapper.toDtos(receiverArticle.getProposerArticles(), ArticleDto.class);
    }

    public Optional<ArticleDto> findById(UUID articleId) {
        Article article = this.articleRepository.findById(articleId).orElseThrow(() -> new RuntimeException("Artículo no encontrado"));
        ArticleDto articleDto = this.mapper.toDto(article, ArticleDto.class);
        return Optional.of(article.setExchangeDetails(articleDto));
    }

    public UUID save(UUID userId, ArticleDto articleDto) {
        UserDto user = userService.findById(userId).orElseThrow(() -> new RuntimeException("Usuario no encontrado"));
        Article article = this.mapper.toModel(articleDto, Article.class);
        article.setUser(this.mapper.toModel(user, User.class));
        return this.mapper.toDto(this.articleRepository.save(article), ArticleDto.class).getId();
    }

    public UUID update(UUID articleId, ArticleDto articleDto) {
        Article article = this.articleRepository.findById(articleId).orElseThrow(() -> new RuntimeException("Artículo no encontrado"));
        Article articleUpdated = this.mapper.toModel(articleDto, Article.class);
        articleUpdated.setId(articleId);
        articleUpdated.setUser(article.getUser());
        articleUpdated.setNumbersProposals(article.getNumbersProposals());
        articleUpdated.setWhereReceived(article.getWhereReceived());
        articleUpdated.setWhereProposed(article.getWhereProposed());
        articleUpdated.setTypeArticle(article.getTypeArticle());
        return this.mapper.toDto(this.articleRepository.save(articleUpdated), ArticleDto.class).getId();
    }

    public void deleteById(UUID articleId) {
        Article article = this.articleRepository.findById(articleId).orElseThrow(() -> new RuntimeException("Artículo no encontrado"));
        article.updateArticlesFromDeleteOrExchanged();
        this.articleRepository.delete(article);
    }

}