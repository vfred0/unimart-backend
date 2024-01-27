package ec.edu.unemi.unimart.data.daos;

import ec.edu.unemi.unimart.data.entities.User;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.UUID;

@Repository
public interface IUserRepository extends IRepository<User, UUID> {
    Optional<User> findByUsernameAndPassword(String username, String password);

    User findOneByUsername(String username);
}