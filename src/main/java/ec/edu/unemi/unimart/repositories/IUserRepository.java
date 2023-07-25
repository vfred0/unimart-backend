package ec.edu.unemi.unimart.repositories;

import ec.edu.unemi.unimart.models.User;

import java.util.Optional;
import java.util.UUID;

public interface IUserRepository extends IRepository<User, UUID> {
    Optional<User> findByUsernameAndPassword(String username, String password);
}