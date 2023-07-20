package ec.edu.unemi.unimart.utils;

import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
@RequiredArgsConstructor
public class Mapper {

    private final ModelMapper mapper;

    public <E, D> D toDto(E entity, Class<D> dtoClass) {
        return mapper.map(entity, dtoClass);
    }

    public <E, D> E toEntity(D dto, Class<E> entityClass) {
        return mapper.map(dto, entityClass);
    }

    public <D, E> List<D> toDtos(List<E> entities, Class<D> dtoClass) {
        return entities.stream()
                .map(entity -> toDto(entity, dtoClass))
                .toList();
    }

}
