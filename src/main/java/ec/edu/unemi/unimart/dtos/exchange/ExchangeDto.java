package ec.edu.unemi.unimart.dtos;

import com.fasterxml.jackson.annotation.JsonInclude;
import jakarta.validation.constraints.Size;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.FieldDefaults;

import java.time.LocalDateTime;
import java.util.UUID;

@Data
@AllArgsConstructor
@NoArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL)
@FieldDefaults(level = AccessLevel.PRIVATE)
public class ExchangeDto {
    UUID id;
    UUID userId;
    @Size(max = 60, message = "El título no puede tener más de 60 caracteres")
    String userName;
    @Size(max = 20, message = "La imagen no puede tener más de 20 caracteres")
    String userPhoto;
    @Size(max = 60, message = "El nombre del artículo a intercambiar no puede tener más de 60 caracteres")
    String articleToExchange;
    @Size(max = 60, message = "El nombre del artículo a recibir no puede tener más de 60 caracteres")
    String articleToReceive;
    Boolean hasBeenRated;
    Boolean isDiscarded;
    String date;
}
