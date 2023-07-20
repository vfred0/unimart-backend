package ec.edu.unemi.unimart.dtos;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonIncludeProperties;
import ec.edu.unemi.unimart.models.User;
import ec.edu.unemi.unimart.utils.Category;
import ec.edu.unemi.unimart.utils.Gender;
import ec.edu.unemi.unimart.utils.State;
import ec.edu.unemi.unimart.utils.TypeArticle;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

@JsonInclude(JsonInclude.Include.NON_NULL)
public record ArticleDto(
        UUID id,

        @JsonIncludeProperties({"id"})
        User user,

        String title,
        String description,
        List<String> images,
        Category category,
        State state,
        Gender gender,
        TypeArticle typeArticle,
        Integer numbersProposals,
        LocalDateTime date
) {
}
