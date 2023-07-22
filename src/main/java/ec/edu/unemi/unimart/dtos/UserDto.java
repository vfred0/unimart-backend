package ec.edu.unemi.unimart.dtos;

import com.fasterxml.jackson.annotation.JsonInclude;
import jakarta.validation.constraints.Size;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.FieldDefaults;

import java.util.UUID;

@Data
@AllArgsConstructor
@NoArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL)
@FieldDefaults(level = AccessLevel.PRIVATE)
public class UserDto {
    UUID id;
    @Size(max = 50, message = "La imagen debe tener máximo 50 caracteres")
    String photo;
    @Size(max = 20, message = "El nombre debe tener máximo 20 caracteres")
    String name;
    @Size(max = 250, message = "La descripción debe tener máximo 100 caracteres")
    String about;
    Integer numberOfExchanges;
    Double rating;
    @Size(min = 10, max = 10, message = "El número de whatsapp debe tener 10 dígitos")
    String numberWhatsapp;
}