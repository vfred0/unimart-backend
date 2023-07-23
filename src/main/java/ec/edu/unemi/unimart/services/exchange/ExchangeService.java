package ec.edu.unemi.unimart.services.exchange;

import ec.edu.unemi.unimart.dtos.ExchangeDto;
import ec.edu.unemi.unimart.models.Exchange;
import ec.edu.unemi.unimart.repositories.IExchangeRepository;
import ec.edu.unemi.unimart.services.crud.CrudService;
import ec.edu.unemi.unimart.mappers.Mapper;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
public class ExchangeService extends CrudService<Exchange, ExchangeDto, UUID> implements IExchangeService {

    public ExchangeService(Mapper mapper, IExchangeRepository repository) {
        super(mapper, repository, Exchange.class, ExchangeDto.class);
    }
}
