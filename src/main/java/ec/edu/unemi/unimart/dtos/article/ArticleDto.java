package ec.edu.unemi.unimart.dtos.article;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonIncludeProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import ec.edu.unemi.unimart.dtos.UserDto;
import ec.edu.unemi.unimart.models.User;
import ec.edu.unemi.unimart.models.enums.Category;
import ec.edu.unemi.unimart.models.enums.Gender;
import ec.edu.unemi.unimart.models.enums.State;
import ec.edu.unemi.unimart.models.enums.TypeArticle;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Null;
import jakarta.validation.constraints.Size;
import lombok.*;
import lombok.experimental.FieldDefaults;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Set;
import java.util.UUID;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@JsonInclude(JsonInclude.Include.NON_NULL)
@FieldDefaults(level = AccessLevel.PRIVATE)
public class ArticleDto {
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
    UUID receiverUserIdForArticle;

    @NotNull
    Set<String> images;

    @JsonProperty(access = JsonProperty.Access.READ_ONLY)
    List<UUID> proposersUserIdsForArticle;

    @NotNull @Builder.Default
    TypeArticle typeArticle = TypeArticle.PUBLISHED;
}