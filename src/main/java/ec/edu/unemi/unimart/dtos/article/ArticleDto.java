package ec.edu.unemi.unimart.dtos.article;

import com.fasterxml.jackson.annotation.JsonProperty;
import ec.edu.unemi.unimart.models.enums.Category;
import ec.edu.unemi.unimart.models.enums.Gender;
import ec.edu.unemi.unimart.models.enums.State;
import ec.edu.unemi.unimart.models.enums.TypeArticle;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

public record ArticleDto(
        UUID id,
        @NotNull @Size(max = 60) String title,
        @NotNull @Size(max = 250) String description,
        @JsonProperty(access = JsonProperty.Access.READ_ONLY)
        Short numbersProposals,
        @JsonProperty(access = JsonProperty.Access.READ_ONLY)
        LocalDateTime date,
        @NotNull Category category,
        @NotNull State state,
        Gender gender,
        @JsonProperty(access = JsonProperty.Access.READ_ONLY)
        Boolean acceptProposals,
        @JsonProperty(access = JsonProperty.Access.READ_ONLY)
        UUID receiverUserIdForArticle,
        @JsonProperty(access = JsonProperty.Access.READ_ONLY)
        List<UUID> proposersUserIdsForArticle,
        @NotNull TypeArticle typeArticle) {
}