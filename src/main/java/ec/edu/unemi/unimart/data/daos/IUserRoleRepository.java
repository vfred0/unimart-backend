package ec.edu.unemi.unimart.data.daos;

import ec.edu.unemi.unimart.data.entities.UserRole;
import ec.edu.unemi.unimart.data.enums.Role;
import org.springframework.data.jpa.repository.Query;

import java.util.Optional;
import java.util.UUID;

public interface IUserRoleRepository extends IRepository<UserRole, UUID> {

    Optional<UserRole> findByName(Role name);
}
