package ec.edu.unemi.unimart.services.user;

import ec.edu.unemi.unimart.dtos.UserDto;
import ec.edu.unemi.unimart.services.crud.ICrudService;

import java.util.UUID;

public interface IUserService extends ICrudService<UserDto, UUID> {
}
