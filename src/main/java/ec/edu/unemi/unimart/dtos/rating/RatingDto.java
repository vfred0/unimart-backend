package ec.edu.unemi.unimart.dtos.rating;

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
public class RatingDto {
    UUID id;
    UUID userId;
    @Size(max = 250, message = "El comentario no puede tener más de 100 caracteres")
    String comment;
    Short score;
}