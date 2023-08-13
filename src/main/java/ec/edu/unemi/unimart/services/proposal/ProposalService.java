package ec.edu.unemi.unimart.services.proposal;

import ec.edu.unemi.unimart.dtos.ProposalDto;
import ec.edu.unemi.unimart.exceptions.MessageException;
import ec.edu.unemi.unimart.exceptions.NotFoundException;
import ec.edu.unemi.unimart.models.Article;
import ec.edu.unemi.unimart.models.Proposal;
import ec.edu.unemi.unimart.repositories.IArticleRepository;
import ec.edu.unemi.unimart.repositories.IProposalRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Objects;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class ProposalService implements IProposalService {

    private final IProposalRepository repository;
    private final IArticleRepository articleRepository;

    @Override
    public UUID save(ProposalDto proposalDto) {
        Article receiverArticle = getArticle(proposalDto.receiverArticleId(), MessageException.RECEIVER_ARTICLE_NOT_FOUND);
        Article proposerArticle = getArticle(proposalDto.proposerArticleId(), MessageException.PROPOSER_ARTICLE_NOT_FOUND);
        Proposal proposal = Proposal.builder()
                .receiverArticle(receiverArticle)
                .proposerArticle(proposerArticle)
                .build();
        UUID id = this.repository.save(proposal).getId();
        Objects.requireNonNull(receiverArticle).addWhereReceived(proposal);
        Objects.requireNonNull(proposerArticle).setProposed();
        this.articleRepository.save(receiverArticle);
        this.articleRepository.save(proposerArticle);
        return id;
    }

    private Article getArticle(UUID id, MessageException messageException) {
        return this.articleRepository.findById(id).orElseThrow(() -> NotFoundException.throwBecauseOf(messageException));
    }

    @Override
    public void deleteByProposerArticleId(UUID proposerArticleId) {
        Article article =  this.getArticle(proposerArticleId, MessageException.PROPOSER_ARTICLE_NOT_FOUND);
        article.getWhereProposed().getReceiverArticle().removeProposer(article);
        article.setPublished();
        this.repository.deleteByProposerArticleId(proposerArticleId);
    }

    @Override
    public void deleteOthersProposals(Proposal proposal) {
        this.repository.deleteOthersProposals(
                proposal.getId(),
                proposal.getReceiverArticle().getId(),
                proposal.getProposerArticle().getId());

    }

    @Override
    public Proposal findByProposalDto(ProposalDto proposalDto) {
        return this.repository.findByReceiverArticleIdAndProposerArticleId(
                proposalDto.receiverArticleId(),
                proposalDto.proposerArticleId()
        );
    }

}