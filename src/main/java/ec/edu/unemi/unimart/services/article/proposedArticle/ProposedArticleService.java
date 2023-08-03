package ec.edu.unemi.unimart.services.article.proposedArticle;

import ec.edu.unemi.unimart.dtos.article.ProposedArticleDto;
import ec.edu.unemi.unimart.models.Article;
import ec.edu.unemi.unimart.models.ProposedArticle;
import ec.edu.unemi.unimart.models.enums.TypeArticle;
import ec.edu.unemi.unimart.repositories.IArticleRepository;
import ec.edu.unemi.unimart.repositories.IProposedArticleRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.UUID;
import java.util.logging.Logger;

@Service
@RequiredArgsConstructor
public class ProposedArticleService implements IProposedArticleService {
    private final IProposedArticleRepository repository;
    private final IArticleRepository articleRepository;

    @Override
    public UUID save(ProposedArticleDto proposedArticleDto) {
        Article receiverArticle = this.articleRepository.findById(proposedArticleDto.receiverArticle()).orElse(null);
        Article proposerArticle = this.articleRepository.findById(proposedArticleDto.proposerArticle()).orElse(null);
        ProposedArticle proposedArticle = ProposedArticle.builder()
                .receiverArticle(receiverArticle)
                .proposerArticle(proposerArticle)
                .build();
        UUID id = this.repository.save(proposedArticle).getId();
        Logger.getLogger("ProposedArticleService").info("Proposed Article: " + proposedArticle);

        receiverArticle.addProposerArticle(proposedArticle);
        proposerArticle.setTypeArticle(TypeArticle.PROPOSED);
        Logger.getLogger("ProposedArticleService").info("Receiver Article: " + receiverArticle);
        return id;
    }

    @Override
    public void deleteByProposerArticleId(UUID proposerArticleId) {
        this.articleRepository.findById(proposerArticleId).ifPresent(article ->
                {
                    article.getWhereProposed().getReceiverArticle().removeProposer(article);
                    article.setTypeArticle(TypeArticle.PUBLISHED);
                }
        );
        this.repository.deleteByProposerArticleId(proposerArticleId);
    }

}