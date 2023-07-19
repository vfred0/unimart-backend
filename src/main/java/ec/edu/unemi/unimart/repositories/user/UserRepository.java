package ec.edu.unemi.unimart.repositories.user;

import ec.edu.unemi.unimart.dtos.UserDto;
import ec.edu.unemi.unimart.mappers.IUserMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;
@RequiredArgsConstructor
@Repository
public class UserRepository implements IUserRepository {
    private final IUserCrudRepository userCrudRepository;
    private final IUserMapper userMapper;

    @Override
    public List<UserDto> findAll() {
        return this.userMapper.toUserDtos(this.userCrudRepository.findAll());
    }

    @Override
    public UUID save(UserDto userDto) {
        return this.userCrudRepository.save(this.userMapper.toUserEntity(userDto)).getId();
    }

    @Override
    public Optional<UserDto> findById(UUID id) {
        return this.userCrudRepository.findById(id).map(this.userMapper::toUserDto);
    }


}
