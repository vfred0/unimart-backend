package ec.edu.unemi.unimart.services.article;

import ec.edu.unemi.unimart.api.dtos.ArticleDto;
import ec.edu.unemi.unimart.services.exceptions.MessageException;
import ec.edu.unemi.unimart.data.utils.Mapper;
import ec.edu.unemi.unimart.data.entities.Article;
import ec.edu.unemi.unimart.data.entities.User;
import ec.edu.unemi.unimart.data.enums.Category;
import ec.edu.unemi.unimart.data.enums.State;
import ec.edu.unemi.unimart.data.daos.IArticleRepository;
import ec.edu.unemi.unimart.services.exceptions.NotFoundException;
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

    @Override
    public List<ArticleDto> search(String title, Category category, State state) {
        return this.mapper.toDtos(this.articleRepository.findByTitleAndCategoryAndState(title, category, state), ArticleDto.class);
    }

    @Override
    public List<ArticleDto> proposerArticlesByReceiverArticleId(UUID receiverArticleId) {
        Article receiverArticle = getArticle(receiverArticleId);
        return this.mapper.toDtos(receiverArticle.getProposerArticles(), ArticleDto.class);
    }

    @Override
    public Optional<ArticleDto> findById(UUID articleId) {
        Article article = getArticle(articleId);
        ArticleDto articleDto = this.mapper.toDto(article, ArticleDto.class);
        return Optional.of(article.setExchangeDetails(articleDto));
    }

    @Override
    public UUID save(UUID userId, ArticleDto articleDto) {
        User user = userService.getUserById(userId);
        Article article = this.mapper.toModel(articleDto, Article.class);
        article.setUser(user);
        return this.mapper.toDto(this.articleRepository.save(article), ArticleDto.class).getId();
    }

    @Override
    public UUID update(UUID articleId, ArticleDto articleDto) {
        Article article = getArticle(articleId);
        Article articleUpdated = this.mapper.toModel(articleDto, Article.class);
        articleUpdated.setId(articleId);
        articleUpdated.setUser(article.getUser());
        articleUpdated.setNumbersProposals(article.getNumbersProposals());
        articleUpdated.setWhereReceived(article.getWhereReceived());
        articleUpdated.setWhereProposed(article.getWhereProposed());
        articleUpdated.setArticleType(article.getArticleType());
        return this.mapper.toDto(this.articleRepository.save(articleUpdated), ArticleDto.class).getId();
    }

    @Override
    public void deleteById(UUID articleId) {
        Article article = getArticle(articleId);
        article.updateArticlesFromDeleteOrExchanged();
        this.articleRepository.delete(article);
    }

    private Article getArticle(UUID id) {
        return this.articleRepository.findById(id).orElseThrow(() -> new NotFoundException(MessageException.ARTICLE_NOT_FOUND));
    }

}