package ec.edu.unemi.unimart.services.crud;

import java.util.List;

public interface ICrudService<D, ID> {
    D save(D dto);
    D update(D dto, ID id);
    void delete(ID id);
    List<D> getAll();
}
