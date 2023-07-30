package ec.edu.unemi.unimart.services.crud;

import java.util.List;
import java.util.Optional;

public interface ICrudService<D, ID> {
    D save(D dto);

    D update(ID id, D dto);

    void deleteById(ID id);

    List<D> getAll();

    Optional<D> findById(ID id);
}
