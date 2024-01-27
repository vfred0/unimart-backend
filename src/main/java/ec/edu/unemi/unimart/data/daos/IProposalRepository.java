package ec.edu.unemi.unimart.data.daos;

import ec.edu.unemi.unimart.data.entities.Proposal;
import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

import java.util.UUID;

public interface IProposalRepository extends IRepository<Proposal, UUID> {
    @Transactional
    @Modifying
    @Query("DELETE FROM Proposal pa WHERE pa.proposerArticle.id = :proposerArticleId")
    void deleteByProposerArticleId(UUID proposerArticleId);

    Proposal findByReceiverArticleIdAndProposerArticleId(UUID receiverArticleId, UUID proposerArticleId);

    @Transactional
    @Modifying
    @Query(value = "CALL proc_delete_others_proposed_articles(:proposedArticleId, :receiverArticleId, :proposerArticleId)", nativeQuery = true)
    void deleteOthersProposals(UUID proposedArticleId, UUID receiverArticleId, UUID proposerArticleId);

}
