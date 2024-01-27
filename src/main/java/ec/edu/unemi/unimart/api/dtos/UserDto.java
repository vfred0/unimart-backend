package ec.edu.unemi.unimart.api.dtos;

import com.fasterxml.jackson.annotation.JsonInclude;
import jakarta.validation.constraints.Size;
import lombok.*;
import lombok.experimental.FieldDefaults;

import java.io.Serializable;
import java.util.UUID;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@JsonInclude(JsonInclude.Include.NON_NULL)
@FieldDefaults(level = AccessLevel.PRIVATE)
public class UserDto {
    UUID id;
    @Size(max = 50) String photo;
    @Size(max = 15) String username;
    @Size(max = 50) String name;
    @Size(max = 250) String about;
    Double rating;
    Short numberExchanges;
    @Size(max = 10) String numberWhatsapp;
    @Size(max = 32) String password;

}

