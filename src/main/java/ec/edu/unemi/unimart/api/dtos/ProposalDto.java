package ec.edu.unemi.unimart.api.dtos;

import java.util.UUID;

public record ProposalDto(
    UUID receiverArticleId,
    UUID proposerArticleId
) {}