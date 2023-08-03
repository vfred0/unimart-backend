package ec.edu.unemi.unimart.services.article.proposedArticle;

import ec.edu.unemi.unimart.dtos.article.ProposedArticleDto;

import java.util.UUID;

public interface IProposedArticleService {
    UUID save(ProposedArticleDto proposedArticleDto);

    void deleteByProposerArticleId(UUID proposerArticleId);
}
