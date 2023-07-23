package ec.edu.unemi.unimart.services.user;

import ec.edu.unemi.unimart.dtos.UserDto;
import ec.edu.unemi.unimart.models.User;
import ec.edu.unemi.unimart.repositories.IUserRepository;
import ec.edu.unemi.unimart.services.crud.CrudService;
import ec.edu.unemi.unimart.mappers.Mapper;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
public class UserService extends CrudService<User, UserDto, UUID> implements IUserService {

    public UserService(Mapper mapper, IUserRepository repository) {
        super(mapper, repository, User.class, UserDto.class);
    }
}