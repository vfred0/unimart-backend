package ec.edu.unemi.unimart.repositories.user;

import ec.edu.unemi.unimart.entities.UserEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface IUserCrudRepository extends JpaRepository<UserEntity, UUID> {
}
