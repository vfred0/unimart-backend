package ec.edu.unemi.unimart.services.user;

import ec.edu.unemi.unimart.api.dtos.ArticleDto;
import ec.edu.unemi.unimart.api.dtos.UserDto;
import ec.edu.unemi.unimart.data.daos.IUserRepository;
import ec.edu.unemi.unimart.data.entities.User;
import ec.edu.unemi.unimart.data.utils.Mapper;
import ec.edu.unemi.unimart.exceptions.MessageException;
import ec.edu.unemi.unimart.exceptions.NotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class UserService implements IUserService {

    private final IUserRepository userRepository;
    private final Mapper mapper;

    @Override
    public UUID save(UserDto userDto) {
        return this.mapper.toDto(this.userRepository.save(this.mapper.toModel(userDto, User.class)), UserDto.class).getId();
    }

    @Override
    public UUID update(UUID userId, UserDto userDto) {
        User user = getUserById(userId);
        User userUpdated = this.mapper.toModel(userDto, User.class);
        userUpdated.setId(user.getId());
        userUpdated.setRating(user.getRating());
        userUpdated.setNumberExchanges(user.getNumberExchanges());
        return this.mapper.toDto(this.userRepository.save(userUpdated), UserDto.class).getId();
    }

    @Override
    public User getUserById(UUID id) {
        return this.userRepository.findById(id).orElseThrow(() -> NotFoundException.throwBecauseOf(MessageException.USER_NOT_FOUND));
    }

    @Override
    public void saveByModel(User user) {
        this.userRepository.save(user);
    }


    @Override
    public Optional<UserDto> findById(UUID id) {
        return this.userRepository.findById(id).map(user ->
                this.mapper.toDto(user, UserDto.class)
        );
    }

    @Override
    public void deleteById(UUID id) {
        this.userRepository.deleteById(id);
    }

    @Override
    public List<ArticleDto> getArticlesByUserId(UUID id) {
        User user = getUserById(id);
        return user.getArticles().stream().map(article ->
                {
                    ArticleDto articleDto = this.mapper.toDto(article, ArticleDto.class);
                    return article.setReceiverArticleIdAndNumberProposals(articleDto);
                }
        ).toList();
    }
}