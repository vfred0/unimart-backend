package ec.edu.unemi.unimart.services.exchange;

import ec.edu.unemi.unimart.dtos.ExchangeDto;
import ec.edu.unemi.unimart.dtos.ExchangeSaveDto;
import ec.edu.unemi.unimart.models.Article;
import ec.edu.unemi.unimart.models.Exchange;
import ec.edu.unemi.unimart.repositories.IArticleRepository;
import ec.edu.unemi.unimart.repositories.IExchangeRepository;
import ec.edu.unemi.unimart.services.crud.CrudService;
import ec.edu.unemi.unimart.mappers.Mapper;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
public class ExchangeService extends CrudService<Exchange, ExchangeDto, UUID> implements IExchangeService {
    private final IArticleRepository articleRepository;
    public ExchangeService(Mapper mapper, IExchangeRepository repository, IArticleRepository articleRepository) {
        super(mapper, repository, Exchange.class, ExchangeDto.class);
        this.articleRepository = articleRepository;
    }

    @Override
    public UUID save(ExchangeSaveDto exchangeSaveDto) {
        Article article = articleRepository.findById(exchangeSaveDto.getArticleId()).orElseThrow(() -> new RuntimeException("Article not found"));
        Article proposedArticle = articleRepository.findById(exchangeSaveDto.getArticleProposedId()).orElseThrow(() -> new RuntimeException("Article proposed not found"));
        Exchange exchange = new Exchange();
        exchange.add(article, proposedArticle);
        return this.getRepository().save(exchange).getId();
    }
}
