package ec.edu.unemi.unimart.dtos;

import java.time.LocalDateTime;
import java.util.UUID;

public record ExchangeDto(
        UUID id,
        String userName,
        String userPhoto,
        String articleToExchange,
        String articleToReceive,
        LocalDateTime date
) {
}
