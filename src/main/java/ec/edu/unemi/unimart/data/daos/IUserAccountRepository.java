package ec.edu.unemi.unimart.data.daos;

import ec.edu.unemi.unimart.data.entities.identity.UserAccount;

import java.util.Optional;
import java.util.UUID;

public interface IUserAccountRepository extends IRepository<UserAccount, UUID> {
    Optional<UserAccount> findByUsername(String username);
}
