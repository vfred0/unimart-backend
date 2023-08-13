package ec.edu.unemi.unimart.services.proposal;

import ec.edu.unemi.unimart.dtos.ProposalDto;
import ec.edu.unemi.unimart.models.Proposal;
import jakarta.transaction.Transactional;

import java.util.UUID;

public interface IProposalService {
    @Transactional
    UUID save(ProposalDto proposalDto);

    @Transactional
    void deleteByProposerArticleId(UUID proposerArticleId);

    @Transactional
    void deleteOthersProposals(Proposal proposal);

    Proposal findByProposalDto(ProposalDto proposalDto);
}
