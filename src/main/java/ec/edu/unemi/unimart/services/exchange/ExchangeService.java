package ec.edu.unemi.unimart.services.exchange;

import ec.edu.unemi.unimart.dtos.ExchangeDto;
import ec.edu.unemi.unimart.dtos.article.ProposedArticleDto;
import ec.edu.unemi.unimart.mappers.Mapper;
import ec.edu.unemi.unimart.models.Article;
import ec.edu.unemi.unimart.models.Exchange;
import ec.edu.unemi.unimart.repositories.IArticleRepository;
import ec.edu.unemi.unimart.repositories.IExchangeRepository;
import ec.edu.unemi.unimart.repositories.IProposedArticleRepository;
import ec.edu.unemi.unimart.repositories.IUserRepository;
import ec.edu.unemi.unimart.services.crud.CrudService;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
public class ExchangeService extends CrudService<Exchange, ExchangeDto, UUID> implements IExchangeService {
    private final IArticleRepository articleRepository;
    private final IUserRepository userRepository;
    private final IProposedArticleRepository proposedArticleRepository;

    public ExchangeService(Mapper mapper, IExchangeRepository repository, IArticleRepository articleRepository, IUserRepository userRepository, IProposedArticleRepository proposedArticleRepository) {
        super(mapper, repository, Exchange.class, ExchangeDto.class);
        this.articleRepository = articleRepository;
        this.userRepository = userRepository;
        this.proposedArticleRepository = proposedArticleRepository;
    }

    protected IExchangeRepository getRepository() {
        return (IExchangeRepository) super.getRepository();
    }

    @Override
    public UUID save(ProposedArticleDto proposedArticleDto) {
        Article article = articleRepository.findById(proposedArticleDto.getArticleId()).orElseThrow(() -> new RuntimeException("Article not found"));
        Article proposedArticle = articleRepository.findById(proposedArticleDto.getProposedArticleId()).orElseThrow(() -> new RuntimeException("Article proposed not found"));
        Exchange exchange = new Exchange();
        exchange.setValues(article, proposedArticle);
        UUID exchangeId = this.getRepository().save(exchange).getId();
        this.getRepository().updateArticlesFromAcceptExchange(exchangeId, article.getId(), proposedArticle.getId());
        return exchangeId;
    }

    @Override
    public List<ExchangeDto> findByUserId(UUID userId) {
        this.userRepository.findById(userId).orElseThrow(() -> new RuntimeException("User not found: " + userId));
        return this.getMapper().toDtos(this.getRepository().findByUserId(userId), ExchangeDto.class);
    }

    @Override
    public void deleteById(UUID id) {
        Exchange exchange = this.getRepository().findById(id).orElseThrow(() -> new RuntimeException("Exchange not found: " + id));
        this.proposedArticleRepository.deleteByExchangeId(id);
        this.getRepository().delete(exchange);
    }
}
