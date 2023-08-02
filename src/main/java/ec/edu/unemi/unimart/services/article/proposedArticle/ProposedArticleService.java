package ec.edu.unemi.unimart.services.article.proposedArticle;

import ec.edu.unemi.unimart.dtos.article.ArticleCardDto;
import ec.edu.unemi.unimart.dtos.article.ArticleDto;
import ec.edu.unemi.unimart.dtos.article.ProposedArticleDto;
import ec.edu.unemi.unimart.mappers.IArticleMapper;
import ec.edu.unemi.unimart.mappers.Mapper;
import ec.edu.unemi.unimart.models.Article;
import ec.edu.unemi.unimart.models.enums.TypeArticle;
import ec.edu.unemi.unimart.repositories.IArticleRepository;
import ec.edu.unemi.unimart.services.article.ArticleService;
import ec.edu.unemi.unimart.services.user.IUserService;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
public class ProposedArticleService extends ArticleService implements IProposedArticleService {
    public ProposedArticleService(Mapper mapper, IArticleRepository repository, IArticleMapper articleMapper, IUserService userService) {
        super(mapper, repository, articleMapper, userService);
    }

    @Override
    public UUID save(ProposedArticleDto proposedArticleDto) {
        Article article = this.getRepository().findById(proposedArticleDto.getArticleId()).orElseThrow(() -> new RuntimeException("Articulo no encontrado"));
        Article proposedArticle = this.getRepository().findById(proposedArticleDto.getProposedArticleId()).orElseThrow(() -> new RuntimeException("Propuesta no encontrada"));
        if (!this.getRepository().existsProposedArticle(proposedArticleDto.getProposedArticleId())) {
            article.addProposedArticle(proposedArticle.getId());
            proposedArticle.setTypeArticle(TypeArticle.PROPOSED);
            return this.getRepository().save(article).getId();
        }
        throw new RuntimeException("Ya has realizado una propuesta con este articulo: " + proposedArticleDto.getProposedArticleId());
    }

    @Override
    public List<ArticleDto> proposedArticlesByArticleId(UUID articleId) {
        Article article = this.getRepository().findById(articleId).orElseThrow(() -> new RuntimeException("Articulo no encontrado"));
        return article.getProposedArticles()
                .stream()
                .map(proposedArticle -> this.getMapper().toDto(this.getRepository().findById(proposedArticle.getProposerArticle().getId()), ArticleDto.class))
                .peek(articleDto ->
                        {
                            List<Object[]> exchangeDetails = this.getRepository().findExchangeDetailsByArticleId(articleDto.getId());
                            articleDto.setAcceptProposals(Boolean.parseBoolean(exchangeDetails.get(0)[0].toString()));
                        }
                )
                .toList();
    }

    public List<ArticleCardDto> proposedArticlesByUserId(UUID userId) {
        return this.getRepository().findAll().stream()
                .filter(article -> article.isToUserAndArticleProposed(userId))
                .map(article -> this.getMapper().toDto(article, ArticleCardDto.class)).toList();
    }

    public void deleteById(UUID proposedArticleId) {
        this.getRepository().findById(proposedArticleId).orElseThrow(() -> new RuntimeException("Articulo no encontrado"));
        this.getRepository().updateTypeArticleFromDeleted(proposedArticleId, true);
        Article article = this.getRepository().getArticleByProposedArticleId(proposedArticleId);
        article.removeProposedArticle(proposedArticleId);
        this.getRepository().deleteProposedArticleById(proposedArticleId);
        this.getRepository().save(article);
    }

    public Boolean userHasMadeProposed(UUID userId, UUID articleId) {
        return this.getRepository().userHasMadeProposed(userId, articleId);
    }

    @Override
    public ArticleDto getArticleByProposedArticleId(UUID proposedArticleId) {
        Article article = this.getRepository().getArticleByProposedArticleId(proposedArticleId);

        if (article == null) {
        }

        return this.getMapper().toDto(article, ArticleDto.class);

    }
}