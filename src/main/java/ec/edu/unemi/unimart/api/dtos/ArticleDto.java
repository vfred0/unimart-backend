package ec.edu.unemi.unimart.api.dtos;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import ec.edu.unemi.unimart.data.enums.Category;
import ec.edu.unemi.unimart.data.enums.Gender;
import ec.edu.unemi.unimart.data.enums.State;
import ec.edu.unemi.unimart.data.enums.TypeArticle;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Null;
import jakarta.validation.constraints.Size;
import lombok.*;
import lombok.experimental.FieldDefaults;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

@FieldDefaults(level = AccessLevel.PRIVATE)
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@JsonInclude(JsonInclude.Include.NON_NULL)
public class ArticleDto {
    @JsonProperty(access = JsonProperty.Access.READ_ONLY)
    UUID id;

    @JsonProperty(access = JsonProperty.Access.READ_ONLY)
    UserDto user;

    @NotNull @Size(max = 60) String title;

    @NotNull @Size(max = 250) String description;

    @JsonProperty(access = JsonProperty.Access.READ_ONLY)
    Short numbersProposals;

    @JsonProperty(access = JsonProperty.Access.READ_ONLY)
    LocalDateTime date;

    @NotNull @Builder.Default
    Category category = Category.TEXT_BOOKS_EDUCATIONAL_MATERIAL;

    @NotNull State state;

    @Null Gender gender;

    @JsonProperty(access = JsonProperty.Access.READ_ONLY)
    Boolean acceptProposals;

    @JsonProperty(access = JsonProperty.Access.READ_ONLY)
    Boolean isAcceptableForExchange;

    @JsonProperty(access = JsonProperty.Access.READ_ONLY)
    UUID receiverUserIdForArticle;

    @JsonProperty(access = JsonProperty.Access.READ_ONLY)
    UUID receiverArticleId;

    @NotNull
    String[] images;

    @JsonProperty(access = JsonProperty.Access.READ_ONLY)
    List<UUID> proposersUserIdsForArticle;

    @NotNull @Builder.Default
    TypeArticle typeArticle = TypeArticle.PUBLISHED;
}