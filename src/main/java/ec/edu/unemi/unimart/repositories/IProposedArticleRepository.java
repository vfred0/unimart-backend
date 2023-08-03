package ec.edu.unemi.unimart.repositories;

import ec.edu.unemi.unimart.dtos.article.ArticleDto;
import ec.edu.unemi.unimart.models.ProposedArticle;
import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.UUID;

public interface IProposedArticleRepository extends IRepository<ProposedArticle, UUID> {

    @Transactional
    @Modifying
    @Query("DELETE FROM ProposedArticle pa WHERE pa.proposerArticle.id = :proposerArticleId")
    void deleteByProposerArticleId(UUID proposerArticleId);

    ProposedArticle findByReceiverArticleIdAndProposerArticleId(UUID receiverArticleId, UUID proposerArticleId);
}
