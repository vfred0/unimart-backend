package ec.edu.unemi.unimart.dtos.article;

import java.util.UUID;

public record ProposedArticleDto (
    UUID receiverArticleId,
    UUID proposerArticleId
) {}