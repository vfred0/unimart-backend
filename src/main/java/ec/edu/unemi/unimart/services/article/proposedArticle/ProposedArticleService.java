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

@Service
@RequiredArgsConstructor
public class ProposedArticleService implements IProposedArticleService {
    private final IProposedArticleRepository repository;
    private final IArticleRepository articleRepository;

    @Override
    public UUID save(ProposedArticleDto proposedArticleDto) {
        Article receiverArticle = this.articleRepository.findById(proposedArticleDto.receiverArticleId()).orElse(null);
        Article proposerArticle = this.articleRepository.findById(proposedArticleDto.proposerArticleId()).orElse(null);
        ProposedArticle proposedArticle = ProposedArticle.builder()
                .receiverArticle(receiverArticle)
                .proposerArticle(proposerArticle)
                .build();
        UUID id = this.repository.save(proposedArticle).getId();
        receiverArticle.addWhereReceived(proposedArticle);
        proposerArticle.setTypeArticle(TypeArticle.PROPOSED);
        this.articleRepository.save(receiverArticle);
        this.articleRepository.save(proposerArticle);
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