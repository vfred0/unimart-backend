package ec.edu.unemi.unimart.repositories;

import ec.edu.unemi.unimart.models.Exchange;
import org.springframework.data.jpa.repository.Query;

import java.util.UUID;

public interface IExchangeRepository extends IRepository<Exchange, UUID> {

//    @Query("SELECT e FROM Exchange e WHERE e.id = :id")
//    UUID accept(UUID id);
}
