package ec.edu.unemi.unimart.services.crud;

import ec.edu.unemi.unimart.repositories.IRepository;
import ec.edu.unemi.unimart.mappers.Mapper;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;

import java.util.List;
import java.util.Optional;

@RequiredArgsConstructor
public abstract class CrudService<M, D, ID> implements ICrudService<D, ID> {
    private final Mapper mapper;
    private final IRepository<M, ID> repository;
    private final Class<M> modelClass;
    private final Class<D> dtoClass;

    @Override
    @Transactional
    public D save(D dto) {
        M model = mapper.toModel(dto, modelClass);
        model = repository.save(model);
        return mapper.toDto(model, dtoClass);
    }

    @Override
    @Transactional
    public D update(ID id, D dto) {
        this.findById(id).orElseThrow(() -> new RuntimeException("Entity not found"));
        return this.save(dto);
    }

    @Override
    public void deleteById(ID id) {
        repository.deleteById(id);
    }

    @Override
    public List<D> getAll() {
        List<M> entities = repository.findAll();
        return mapper.toDtos(entities, dtoClass);
    }

    @Override
    public Optional<D> findById(ID id) {
        return repository.findById(id).map(model -> mapper.toDto(model, dtoClass));
    }

    protected IRepository<M, ID> getRepository() {
        return repository;
    }

    protected Mapper getMapper() {
        return mapper;
    }
}