package ec.edu.unemi.unimart.dtos.article;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.*;
import lombok.experimental.FieldDefaults;

import java.util.UUID;

public record ProposedArticleDto (
    UUID receiverArticle,
    UUID proposerArticle
) {}