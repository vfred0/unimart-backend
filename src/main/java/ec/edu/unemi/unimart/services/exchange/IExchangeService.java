package ec.edu.unemi.unimart.services.exchange;

import ec.edu.unemi.unimart.dtos.ExchangeDto;
import ec.edu.unemi.unimart.services.crud.ICrudService;

import javax.xml.crypto.dsig.Manifest;
import java.util.UUID;

public interface IExchangeService extends ICrudService<ExchangeDto, UUID> {
//    UUID accept(UUID id);
}
