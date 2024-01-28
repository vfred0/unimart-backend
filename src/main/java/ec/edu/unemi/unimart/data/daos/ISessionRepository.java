package ec.edu.unemi.unimart.data.daos;

import ec.edu.unemi.unimart.data.entities.Session;

import java.util.Optional;
import java.util.UUID;

public interface ISessionRepository extends IRepository<Session, UUID> {
    Optional<Session> findByTokenValue(String tokenValue);
}
