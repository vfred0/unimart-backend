package ec.edu.unemi.unimart.dtos.login;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.*;
import lombok.experimental.FieldDefaults;

import java.util.UUID;

public record LoginResponseDto(
        UUID id
) {
}
