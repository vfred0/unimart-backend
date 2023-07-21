package ec.edu.unemi.unimart.dtos;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonIncludeProperties;
import ec.edu.unemi.unimart.models.User;
import ec.edu.unemi.unimart.utils.Category;
import ec.edu.unemi.unimart.utils.Gender;
import ec.edu.unemi.unimart.utils.State;
import ec.edu.unemi.unimart.utils.TypeArticle;
import jakarta.validation.constraints.Null;
import jakarta.validation.constraints.Size;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.FieldDefaults;


import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

@Data
@AllArgsConstructor
@NoArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL)
@FieldDefaults(level = AccessLevel.PRIVATE)
public class ArticleDto {
    UUID id;
    UUID userId;
    @Size(max = 60, message = "El título no puede tener más de 60 caracteres")
    String title;
    @Size(max = 100, message = "La descripción no puede tener más de 100 caracteres")
    String description;
    List<String> images;
    @Size(max = 20, message = "La categoría no puede tener más de 20 caracteres")
    Category category;
    @Size(max = 10, message = "El estado no puede tener más de 10 caracteres")
    State state;
    @Size(max = 10, message = "El género no puede tener más de 10 caracteres")
    @Null
    Gender gender;
    @Size(max = 10, message = "El tipo de artículo no puede tener más de 10 caracteres")
    TypeArticle typeArticle;
    Integer numbersProposals;
    LocalDateTime date;
}