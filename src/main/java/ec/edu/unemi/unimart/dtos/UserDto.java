package ec.edu.unemi.unimart.dtos;

import java.util.UUID;

public record UserDto(
        UUID id,
        String photo,
        String name,
        String about,
        Integer numberOfExchanges,
        Double rating,
        String numberWhatsapp
) {
}
