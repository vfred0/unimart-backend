package ec.edu.unemi.unimart.services.proposal;

import ec.edu.unemi.unimart.api.dtos.ProposalDto;
import ec.edu.unemi.unimart.data.entities.Proposal;
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
