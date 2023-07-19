package ec.edu.unemi.unimart.services.user;

import ec.edu.unemi.unimart.dtos.UserDto;
import ec.edu.unemi.unimart.repositories.user.IUserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.UUID;
@RequiredArgsConstructor
@Service
public class UserService implements IUserService {

    private final IUserRepository userRepository;

    @Override
    public List<UserDto> getAll() {
        return this.userRepository.findAll();
    }

    @Override
    public UUID save(UserDto userDto) {
        return this.userRepository.save(userDto);
    }

    @Override
    public Optional<UserDto> findById(UUID id) {
        return this.userRepository.findById(id);
    }
}
