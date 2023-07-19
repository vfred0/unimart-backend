package ec.edu.unemi.unimart.services.user;

import ec.edu.unemi.unimart.dtos.UserDto;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface IUserService {
    List<UserDto> getAll();

    UUID save(UserDto userDto);

    Optional<UserDto> findById(UUID id);
}
