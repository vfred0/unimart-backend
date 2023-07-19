package ec.edu.unemi.unimart.dtos;

import ec.edu.unemi.unimart.entities.UserEntity;
import ec.edu.unemi.unimart.utils.Category;
import ec.edu.unemi.unimart.utils.Gender;
import ec.edu.unemi.unimart.utils.State;
import ec.edu.unemi.unimart.utils.TypeArticle;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

public record ArticleDto(
        UUID id,
        UUID userId,
        String title,
        String description,
        List<String>images,
        Category category,
        State state,
        Gender gender,
        TypeArticle typeArticle,
        Integer numbersProposals,
        LocalDateTime date
) {
}
