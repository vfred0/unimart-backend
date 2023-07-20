package ec.edu.unemi.unimart.services.crud;

import ec.edu.unemi.unimart.repositories.IRepository;
import ec.edu.unemi.unimart.utils.Mapper;
import lombok.RequiredArgsConstructor;

import java.util.List;

@RequiredArgsConstructor
public abstract class CrudService<E, D, ID> implements ICrudService<D, ID> {
    private final Mapper mapper;
    private final IRepository<E, ID> repository;
    private final Class<E> entityClass;
    private final Class<D> dtoClass;

    @Override
    public D save(D dto) {
        E entity = mapper.toEntity(dto, entityClass);
        entity = repository.save(entity);
        return mapper.toDto(entity, dtoClass);
    }

    @Override
    public D update(D dto, ID id) {
        E entity = mapper.toEntity(dto, entityClass);
        entity = repository.save(entity);
        return mapper.toDto(entity, dtoClass);
    }

    @Override
    public void delete(ID id) {
        repository.deleteById(id);
    }

    @Override
    public List<D> getAll() {
        List<E> entities = repository.findAll();
        return mapper.toDtos(entities, dtoClass);
    }

    protected IRepository<E, ID> getRepository() {
        return repository;
    }

    protected Mapper getMapper() {
        return mapper;
    }

}