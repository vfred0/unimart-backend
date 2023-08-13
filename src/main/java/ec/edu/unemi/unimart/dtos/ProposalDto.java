package ec.edu.unemi.unimart.dtos;

import java.util.UUID;

public record ProposalDto(
    UUID receiverArticleId,
    UUID proposerArticleId
) {}