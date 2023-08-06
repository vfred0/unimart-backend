package ec.edu.unemi.unimart.services.user;

import ec.edu.unemi.unimart.dtos.UserDto;
import ec.edu.unemi.unimart.dtos.article.ArticleDto;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface IUserService {

    UUID save(UserDto userDto);

    UUID update(UUID userId, UserDto userDto);

    Optional<UserDto> findById(UUID id);

    void deleteById(UUID id);

    List<ArticleDto> getArticlesByUserId(UUID id);
}