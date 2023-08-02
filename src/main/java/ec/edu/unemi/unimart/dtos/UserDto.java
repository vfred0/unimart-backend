package ec.edu.unemi.unimart.dtos;

import jakarta.validation.constraints.Size;

import java.io.Serializable;
import java.util.UUID;

public record UserDto(
        UUID id,
        @Size(max = 50) String photo,
        @Size(max = 15) String username,
        @Size(max = 50) String name,
        @Size(max = 250) String about,
        Double rating,
        Short numberExchanges,
        @Size(max = 10) String numberWhatsapp,
        @Size(max = 32) String password) {
}