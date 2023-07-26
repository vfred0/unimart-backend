package ec.edu.unemi.unimart.dtos.article;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.constraints.Size;
import lombok.*;
import lombok.experimental.FieldDefaults;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@JsonInclude(JsonInclude.Include.NON_NULL)
@FieldDefaults(level = AccessLevel.PRIVATE)
public class ArticleCardDto {
    @JsonProperty(access = JsonProperty.Access.READ_ONLY)
    UUID id;
    @Size(max = 60, message = "El título no puede tener más de 60 caracteres")
    String title;
    @Size(max = 250, message = "La descripción no puede tener más de 100 caracteres")
    String description;
    List<String> images;
    @Size(max = 50, message = "La categoría no puede tener más de 20 caracteres")
    String category;
    @Size(max = 15, message = "El estado no puede tener más de 15 caracteres")
    String state;
    @Size(max = 10, message = "El género no puede tener más de 10 caracteres")
    @JsonProperty(access = JsonProperty.Access.READ_ONLY)
    String gender;
    @Size(max = 10, message = "El tipo de artículo no puede tener más de 10 caracteres")
    String typeArticle;
    @JsonProperty(access = JsonProperty.Access.READ_ONLY)
    Short numbersProposals;
    @JsonProperty(access = JsonProperty.Access.READ_ONLY)
    LocalDateTime date = LocalDateTime.now();
}