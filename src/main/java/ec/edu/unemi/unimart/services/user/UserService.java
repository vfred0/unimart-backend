package ec.edu.unemi.unimart.services.user;

import ec.edu.unemi.unimart.dtos.UserDto;
import ec.edu.unemi.unimart.models.User;
import ec.edu.unemi.unimart.repositories.IUserRepository;
import ec.edu.unemi.unimart.services.crud.CrudService;
import ec.edu.unemi.unimart.mappers.Mapper;
import org.springframework.stereotype.Service;

import java.util.Optional;
import java.util.UUID;

@Service
public class UserService extends CrudService<User, UserDto, UUID> implements IUserService {

    public UserService(Mapper mapper, IUserRepository repository) {
        super(mapper, repository, User.class, UserDto.class);
    }

    @Override
    public UserDto update(UUID userId, UserDto userDto) {
        User user = this.getRepository().findById(userId).orElseThrow(() -> new RuntimeException("Entity not found"));
        User userUpdated = this.getMapper().toModel(userDto, User.class);
        userUpdated.setId(user.getId());
        userUpdated.setRating(user.getRating());
        userUpdated.setNumberExchanges(user.getNumberExchanges());
        return this.getMapper().toDto(this.getRepository().save(userUpdated), UserDto.class);
    }

    @Override
    public Optional<UserDto> findById(UUID id) {
        return this.getRepository().findById(id).map(user ->
                this.getMapper().toDto(user, UserDto.class)
        );
    }
}