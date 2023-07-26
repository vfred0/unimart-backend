package ec.edu.unemi.unimart.services.exchange;

import ec.edu.unemi.unimart.dtos.ExchangeDto;
import ec.edu.unemi.unimart.dtos.ExchangeSaveDto;
import ec.edu.unemi.unimart.services.crud.ICrudService;

import java.util.List;
import java.util.UUID;

public interface IExchangeService extends ICrudService<ExchangeDto, UUID> {
    UUID save(ExchangeSaveDto exchangeSaveDto);

    List<ExchangeDto> findByUserId(UUID id);
}
