package ec.edu.unemi.unimart.mappers;

import ec.edu.unemi.unimart.dtos.ExchangeDto;
import ec.edu.unemi.unimart.entities.ExchangeEntity;
import org.mapstruct.InheritInverseConfiguration;
import org.mapstruct.Mapper;
@Mapper(componentModel = "spring")
public interface IExchangeMapper {
    ExchangeDto toExchangeDto(ExchangeEntity exchange);

    @InheritInverseConfiguration
    ExchangeEntity toExchangeEntity(ExchangeDto exchange);
}
