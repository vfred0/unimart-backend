package ec.edu.unemi.unimart.repositories.user;

import ec.edu.unemi.unimart.dtos.UserDto;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface IUserRepository {
    List<UserDto> findAll();

    UUID save(UserDto userDto);

    Optional<UserDto> findById(UUID id);
}
